/**
 * Project: parent
 * Document: EnterprisePayProcess
 * Date: 2020/8/22 11:45
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.service.impl;

import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.platform.commons.payment.beans.StatusType;
import com.ixiachong.platform.commons.payment.request.EnterprisePaymentRequest;
import com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse;
import com.ixiachong.platform.commons.payment.response.ResponseErrorCode;
import com.ixiachong.platform.ms.payment.wx.exceptions.Errors;
import com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentError;
import com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentThirdRequest;
import com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentThirdResponse;
import com.ixiachong.platform.ms.payment.wx.service.MessageProcessor;
import com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentOrder;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
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
public class EnterprisePayProcess extends AbstractService {

    private EnterprisePaymentRequest request;

    private EntPayRequest entPayRequest;

    private EnterprisePaymentOrder enterprisePaymentOrder;

    private String tradeNo;

    private ResponseErrorCode<EnterprisePaymentResponse> response;

    private MessageProcessor messageProcessor;

    private Context context;

    ResponseErrorCode<EnterprisePaymentResponse> process() {
        saveRequest();
        initAndVerity();
        getResultAndFlush();
        saveResponse();
        sendMessage();
        return this.getResponse();
    }


    private void saveRequest(){
        context.saveRequest.accept(setCreatedEntityProperties(BeanUtils.mapping(request, com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentRequest.class)));
    }

    private void saveResponse(){
        com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentResponse
                enterprisePaymentResponse = BeanUtils.mapping(response.getResponse(), com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentResponse.class);
        if (enterprisePaymentResponse != null){
            context.saveResponse.accept(setCreatedEntityProperties(enterprisePaymentResponse));
        }
    }


    private void initAndVerity() {
        String openId = context.payeeFinder.apply(request.getPayeeNo(), "wxpay").get("openId");

        if (enterprisePaymentOrder == null) {
            enterprisePaymentOrder = setCreatedEntityProperties(new EnterprisePaymentOrder());
            enterprisePaymentOrder.setOpenId(openId);
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
        if (StringUtils.isEmpty(openId)) {
            response = new ResponseErrorCode<>() {{
                setErrorCode(Errors.PAYMENT_ACCOUNT_NOT_EXISTS.getCode());
                setErrorMessage(Errors.PAYMENT_ACCOUNT_NOT_EXISTS.getMessage());
                setResponse(getErrorResult());
            }};
            return;
        }

        if (!openId.equals(enterprisePaymentOrder.getOpenId())){
            this.response = new ResponseErrorCode<>(){{
                setErrorCode(Errors.PAYMENT_ACCOUNT_IS_CHANGED.getCode());
                setErrorMessage(Errors.PAYMENT_ACCOUNT_IS_CHANGED.getMessage());
                setResponse(getErrorResult());
            }};
            return;
        }

        if ( request.getAmount().compareTo(enterprisePaymentOrder.getAmount()) != 0) {
            response = new ResponseErrorCode<>() {{
                setErrorCode(Errors.PAYMENT_ORDER_AMOUNT_CHANGE.getCode());
                setErrorMessage(Errors.PAYMENT_ORDER_AMOUNT_CHANGE.getMessage());
                setResponse(getErrorResult());
            }};
        }
    }
    private EnterprisePaymentResponse getErrorResult(){
        return new EnterprisePaymentResponse(){{
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
                setResponse(BeanUtils.mapping(enterprisePaymentOrder, EnterprisePaymentResponse.class));
            }};
            return;
        }
        EntPayRequest entPayRequest = new EntPayRequest();
        entPayRequest.setOpenid(enterprisePaymentOrder.getOpenId());
        entPayRequest.setAmount((request.getAmount().multiply(new BigDecimal(100))).intValue());
        entPayRequest.setCheckName("NO_CHECK");
        entPayRequest.setSpbillCreateIp("39.108.120.19");
        entPayRequest.setPartnerTradeNo(request.getOutTradeNo());
        entPayRequest.setDescription(request.getBody());
        this.entPayRequest = entPayRequest;
        enterprisePaymentOrder.setState(StatusType.PROCESSING.getCode());
        wxpayPost();
    }

    private void wxpayPost() {
        System.out.println(entPayRequest.getSpbillCreateIp());
        context.saveThirdRequest.accept(setCreatedEntityProperties(BeanUtils.mapping(entPayRequest,EnterprisePaymentThirdRequest.class)));

        EntPayResult entPayResult;
        try {
            entPayResult = context.wxClientFinder.apply(request.getMerchantNo()).getEntPayService().entPay(entPayRequest);
        } catch (WxPayException e) {
            enterprisePaymentOrder.setState(StatusType.FAILURE.getCode());
            enterprisePaymentOrder.setReason(e.getErrCodeDes().length() > 255 ?
                    e.getErrCodeDes().substring(0,254) : e.getErrCodeDes());
            log.info("=================>微信调用异常:{}" + e.getErrCodeDes());
            context.saveOrder.apply(enterprisePaymentOrder);

            EnterprisePaymentError enterprisePaymentError = new EnterprisePaymentError();
            enterprisePaymentError.setOutTradeNo(request.getOutTradeNo());
            enterprisePaymentError.setErrorCode(e.getErrCode());
            enterprisePaymentError.setErrorMessage(e.getErrCodeDes().length() > 255 ?
                    e.getErrCodeDes().substring(0,254) : e.getErrCodeDes());
            context.saveError.accept(enterprisePaymentError);

            response = new ResponseErrorCode<>() {{
                setErrorCode(e.getErrCode());
                setErrorMessage(e.getErrCodeDes());
                setResponse(getErrorResult());
            }};
            return;
        }
        if (StatusType.SUCCESS.toString().equalsIgnoreCase(entPayResult.getReturnCode())
                && StatusType.SUCCESS.toString().equalsIgnoreCase(entPayResult.getResultCode())) {
            enterprisePaymentOrder.setState(StatusType.SUCCESS.getCode());
            enterprisePaymentOrder.setPaymentNo(entPayResult.getPaymentNo());
            enterprisePaymentOrder.setPaymentTime(new Date());
            response = new ResponseErrorCode<>() {{
                setResponse(BeanUtils.mapping(context.saveOrder.apply(enterprisePaymentOrder), EnterprisePaymentResponse.class));
            }};

           context.saveThirdResponse.accept(setCreatedEntityProperties(BeanUtils.mapping(entPayResult,
            com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentThirdResponse.class)));

            return;
        } else if (StatusType.SUCCESS.toString().equalsIgnoreCase(entPayResult.getReturnCode())) {
            enterprisePaymentOrder.setState(StatusType.FAILURE.getCode());
            enterprisePaymentOrder.setReason(entPayResult.getErrCodeDes());

            EnterprisePaymentError enterprisePaymentError = new EnterprisePaymentError();
            enterprisePaymentError.setOutTradeNo(request.getOutTradeNo());
            enterprisePaymentError.setErrorCode(entPayResult.getErrCode());
            enterprisePaymentError.setErrorMessage(entPayResult.getErrCodeDes().length() > 255 ?
                    entPayResult.getErrCodeDes().substring(0,254) : entPayResult.getErrCodeDes());
            context.saveError.accept(enterprisePaymentError);

            response = new ResponseErrorCode<>() {{
                setErrorCode(entPayResult.getErrCode());
                setErrorMessage(entPayResult.getErrCodeDes().length() > 255 ?
                        entPayResult.getErrCodeDes().substring(0,254) : entPayResult.getErrCodeDes());
                setResponse(BeanUtils.mapping(context.saveOrder.apply(enterprisePaymentOrder), EnterprisePaymentResponse.class));
            }};
            return;
        }

        EnterprisePaymentError enterprisePaymentError = new EnterprisePaymentError();
        enterprisePaymentError.setOutTradeNo(request.getOutTradeNo());
        enterprisePaymentError.setErrorCode(entPayResult.getErrCode());
        enterprisePaymentError.setErrorMessage(entPayResult.getErrCodeDes().length() > 255 ?
                entPayResult.getErrCodeDes().substring(0,254) : entPayResult.getErrCodeDes());
        context.saveError.accept(enterprisePaymentError);

        enterprisePaymentOrder.setState(StatusType.FAILURE.getCode());
        response = new ResponseErrorCode<>() {{
            setErrorCode(entPayResult.getErrCode());
            setErrorMessage(entPayResult.getErrCodeDes().length() > 255 ? entPayResult.getErrCodeDes().substring(0,254) :entPayResult.getErrCodeDes());
            setResponse(BeanUtils.mapping(context.saveOrder.apply(enterprisePaymentOrder), EnterprisePaymentResponse.class));
        }};
    }

    private void sendMessage(){
        messageProcessor.sendMessage(response);
    }




    @Builder
    static class Context{

        private Function<String, WxPayService> wxClientFinder;

        private BiFunction<String, String, Map<String, String>> payeeFinder;

        private Function<EnterprisePaymentOrder, EnterprisePaymentOrder> saveOrder;

        private Consumer<EnterprisePaymentThirdRequest> saveThirdRequest;

        private Consumer<EnterprisePaymentThirdResponse> saveThirdResponse;

        private Consumer<EnterprisePaymentError> saveError;

        private Consumer<com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentRequest> saveRequest;

        private Consumer<com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentResponse> saveResponse;

    }

}
