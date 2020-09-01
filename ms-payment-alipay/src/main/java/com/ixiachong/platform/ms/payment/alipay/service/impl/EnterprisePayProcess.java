/**
 * Project: parent
 * Document: EnterprisePayProcess
 * Date: 2020/8/22 11:45
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.platform.commons.payment.beans.StatusType;
import com.ixiachong.platform.commons.payment.response.ResponseErrorCode;
import com.ixiachong.platform.ms.payment.alipay.exceptions.Errors;
import com.ixiachong.platform.ms.payment.alipay.model.EnterprisePaymentError;
import com.ixiachong.platform.ms.payment.alipay.model.EnterprisePaymentRequest;
import com.ixiachong.platform.ms.payment.alipay.model.EnterprisePaymentResponse;
import com.ixiachong.platform.ms.payment.alipay.model.EnterprisePaymentThirdRequest;
import com.ixiachong.platform.ms.payment.alipay.model.EnterprisePaymentThirdResponse;
import com.ixiachong.platform.ms.payment.alipay.service.MessageProcessor;
import com.ixiachong.platform.ms.payment.alipay.model.EnterprisePaymentOrder;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Author: wangcy
 */
@EqualsAndHashCode(callSuper = false)
@Builder
@Log
@Data
class EnterprisePayProcess extends AbstractService {

    private com.ixiachong.platform.commons.payment.request.EnterprisePaymentRequest request;

    private EnterprisePaymentOrder enterprisePaymentOrder;

    private String tradeNo;

    private AlipayFundTransUniTransferRequest alipayRequestEntity;

    private ResponseErrorCode<com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse> response;

    private MessageProcessor messageProcessor;

    private Context context;

    ResponseErrorCode<com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse> process() {
        saveRequest();
        initAndVerity();
        getResultAndFlush();
        sendMessage();
        saveResponse();
        return this.getResponse();
    }

    private void saveRequest(){
        context.saveRequest.accept(setCreatedEntityProperties(BeanUtils.mapping(request, EnterprisePaymentRequest.class)));
    }

    private void saveResponse(){
        EnterprisePaymentResponse enterprisePaymentResponse = BeanUtils.mapping(response.getResponse(), EnterprisePaymentResponse.class);
        if (enterprisePaymentResponse != null){
            context.saveResponse.accept(setCreatedEntityProperties(enterprisePaymentResponse));
        }
    }

    private void initAndVerity() {
        String alipayUserId = context.payeeFinder.apply(request.getPayeeNo(), "alipay").get("alipayUserId");
        if (enterprisePaymentOrder == null) {
            enterprisePaymentOrder = setCreatedEntityProperties(new EnterprisePaymentOrder());
            enterprisePaymentOrder.setPayUserId(alipayUserId);
            enterprisePaymentOrder.setOutTradeNo(request.getOutTradeNo());
            enterprisePaymentOrder.setAmount(request.getAmount());
            enterprisePaymentOrder.setDescription(request.getBody());
            enterprisePaymentOrder.setTransferTime(new Date());
            enterprisePaymentOrder.setTradeNo(tradeNo);
            enterprisePaymentOrder.setMerchantNo(request.getMerchantNo());
            enterprisePaymentOrder.setState(StatusType.PROCESSING.getCode());
            context.saveOrder.apply(enterprisePaymentOrder);
        } else {
            enterprisePaymentOrder = setEntityProperties(enterprisePaymentOrder);
        }

        if (StringUtils.isEmpty(alipayUserId)) {
            this.response = new ResponseErrorCode<>(){{
                setErrorCode(Errors.PAYMENT_ACCOUNT_NOT_EXISTS.getCode());
                setErrorMessage(Errors.PAYMENT_ACCOUNT_NOT_EXISTS.getMessage());
                setResponse(getErrorResult());
            }};
            return;
        }

        if (!alipayUserId.equals(enterprisePaymentOrder.getPayUserId())){
            this.response = new ResponseErrorCode<>(){{
                setErrorCode(Errors.PAYMENT_ACCOUNT_IS_CHANGED.getCode());
                setErrorMessage(Errors.PAYMENT_ACCOUNT_IS_CHANGED.getMessage());
                setResponse(getErrorResult());
            }};
            return;
        }

        if (null != enterprisePaymentOrder.getAmount() && request.getAmount().compareTo(enterprisePaymentOrder.getAmount()) != 0) {
            this.response = new ResponseErrorCode<>(){{
                setErrorCode(Errors.PAYMENT_ORDER_AMOUNT_CHANGE.getCode());
                setErrorMessage(Errors.PAYMENT_ORDER_AMOUNT_CHANGE.getMessage());
                setResponse(getErrorResult());
            }};
        }
    }

    private com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse getErrorResult(){
        return new com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse(){{
            setOutTradeNo(request.getOutTradeNo());
            setAmount(request.getAmount());
            setTradeNo(enterprisePaymentOrder.getTradeNo());
            setState(StatusType.FAILURE.getCode());
        }};
    }


    private void getResultAndFlush() {
        if (response != null) {
            return;
        }
        if (StatusType.SUCCESS.getCode().equals(enterprisePaymentOrder.getState())) {
            response = new ResponseErrorCode<>() {{
                setResponse(BeanUtils.mapping(enterprisePaymentOrder, com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse.class));
            }};
            return;
        }

        AlipayFundTransUniTransferRequest alipayFundTransUniTransferRequest = new AlipayFundTransUniTransferRequest();
        AlipayFundTransUniTransferModel alipayFundTransUniTransferModel = new AlipayFundTransUniTransferModel();
        alipayFundTransUniTransferModel.setOutBizNo(request.getOutTradeNo());
        alipayFundTransUniTransferModel.setTransAmount(request.getAmount().toPlainString());
        alipayFundTransUniTransferModel.setProductCode("TRANS_ACCOUNT_NO_PWD");
        alipayFundTransUniTransferModel.setOrderTitle(request.getSummary());

        Participant participant = new Participant();
        participant.setIdentity(enterprisePaymentOrder.getPayUserId());
        participant.setIdentityType("ALIPAY_USER_ID");

        alipayFundTransUniTransferModel.setPayeeInfo(participant);
        alipayFundTransUniTransferModel.setRemark(request.getBody());
        alipayFundTransUniTransferModel.setBizScene("DIRECT_TRANSFER");
        alipayFundTransUniTransferRequest.setBizModel(alipayFundTransUniTransferModel);
        this.alipayRequestEntity = alipayFundTransUniTransferRequest;

        enterprisePaymentOrder.setState(StatusType.PROCESSING.getCode());
        alipayPost();
    }

    private void alipayPost() {

        AlipayFundTransUniTransferModel bizModel = (AlipayFundTransUniTransferModel) alipayRequestEntity.getBizModel();
        EnterprisePaymentThirdRequest enterprisePaymentThirdRequest = BeanUtils.mapping(bizModel, EnterprisePaymentThirdRequest.class);
        enterprisePaymentThirdRequest.setIdentity(bizModel.getPayeeInfo().getIdentity());
        enterprisePaymentThirdRequest.setIdentityType(bizModel.getPayeeInfo().getIdentityType());
        context.saveThirdRequest.accept(setCreatedEntityProperties(enterprisePaymentThirdRequest));

        try {
            AlipayFundTransUniTransferResponse executeResponse = context.alipayClientFinder.apply(request.getMerchantNo()).certificateExecute(alipayRequestEntity);
            if ("10000".equals(executeResponse.getCode())) {
                enterprisePaymentOrder.setState(StatusType.SUCCESS.getCode());
                enterprisePaymentOrder.setPaymentNo(executeResponse.getOrderId());
                enterprisePaymentOrder.setPaymentTime(new Date());

                context.saveThirdResponse.accept(setCreatedEntityProperties(BeanUtils.mapping(executeResponse,EnterprisePaymentThirdResponse.class)));

                this.response = new ResponseErrorCode<>() {
                    {
                        setResponse(BeanUtils.mapping(context.saveOrder.apply(enterprisePaymentOrder), com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse.class));
                    }
                };
                return;
            }
            enterprisePaymentOrder.setState(StatusType.FAILURE.getCode());
            enterprisePaymentOrder.setReason(executeResponse.getSubMsg().length() > 255 ?
                    executeResponse.getSubMsg().substring(0,254) : executeResponse.getSubMsg());
            context.saveOrder.apply(enterprisePaymentOrder);

            EnterprisePaymentError enterprisePaymentError = new EnterprisePaymentError();
            enterprisePaymentError.setOutTradeNo(request.getOutTradeNo());
            enterprisePaymentError.setErrorCode(executeResponse.getSubCode());
            enterprisePaymentError.setErrorMessage(executeResponse.getSubMsg().length() > 255 ? executeResponse.getSubMsg().substring(0,254) : executeResponse.getSubMsg());

            context.saveError.accept(setCreatedEntityProperties(enterprisePaymentError));

            this.response = new ResponseErrorCode<>() {
                {
                    setErrorCode(executeResponse.getSubCode());
                    setErrorMessage(executeResponse.getSubMsg());
                    setResponse(getErrorResult());
                }
            };
        } catch (AlipayApiException e) {
            enterprisePaymentOrder.setState(StatusType.FAILURE.getCode());
            enterprisePaymentOrder.setReason(e.getErrMsg().substring(0,254));

            EnterprisePaymentError enterprisePaymentError = new EnterprisePaymentError();
            enterprisePaymentError.setOutTradeNo(request.getOutTradeNo());
            enterprisePaymentError.setErrorCode(e.getErrCode());
            enterprisePaymentError.setErrorMessage(e.getErrMsg().length() > 255 ? e.getErrMsg().substring(0,254) : e.getErrMsg());

            context.saveError.accept(setCreatedEntityProperties(enterprisePaymentError));

            this.response = new ResponseErrorCode<>() {{
                setErrorCode(e.getErrCode());
                setErrorMessage(e.getErrMsg().length() > 255 ? e.getErrMsg().substring(0,254) : e.getErrMsg());
                setResponse(BeanUtils.mapping(context.saveOrder.apply(enterprisePaymentOrder), com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse.class));
            }};
        }
    }

    private void sendMessage(){
        messageProcessor.sendMessage(response);
    }


    @Builder
    static class Context{

        private Function<String, AlipayClient> alipayClientFinder;

        private BiFunction<String, String, Map<String, String>> payeeFinder;

        private Function<EnterprisePaymentOrder, EnterprisePaymentOrder> saveOrder;

        private Consumer<EnterprisePaymentThirdRequest> saveThirdRequest;

        private Consumer<EnterprisePaymentThirdResponse> saveThirdResponse;

        private Consumer<EnterprisePaymentError> saveError;

        private Consumer<EnterprisePaymentRequest> saveRequest;

        private Consumer<EnterprisePaymentResponse> saveResponse;

    }


}
