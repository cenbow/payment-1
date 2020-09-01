/**
 * Project: parent
 * Document: TransfersServiceImpl
 * Date: 2020/8/4 14:41
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.platform.commons.payment.beans.StatusType;
import com.ixiachong.platform.commons.payment.request.EnterprisePaymentRequest;
import com.ixiachong.platform.commons.payment.response.EnterprisePayment;
import com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse;
import com.ixiachong.platform.commons.payment.response.ResponseErrorCode;
import com.ixiachong.platform.ms.payment.alipay.exceptions.Errors;
import com.ixiachong.platform.ms.payment.alipay.exceptions.PaymentException;
import com.ixiachong.platform.ms.payment.alipay.service.MessageProcessor;
import com.ixiachong.platform.ms.payment.alipay.model.EnterprisePaymentOrder;
import com.ixiachong.platform.ms.payment.alipay.service.EntPayService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: wangcy
 */
@Service
@Log4j2
public class EntPayServiceImpl extends AbstractService implements EntPayService {

    @Resource(name = "executorService")
    ExecutorService executorService;

    @Autowired
    MessageProcessor messageProcessor;

    @Override
    public EnterprisePaymentResponse transfers(EnterprisePaymentRequest request) throws PaymentException {

        EnterprisePaymentOrder byOutTradeNo = entPayDao.getByOutTradeNo(request.getOutTradeNo());

        String tradeNo = byOutTradeNo == null ? UUID.randomUUID().toString() : byOutTradeNo.getTradeNo();

        Callable<ResponseErrorCode<EnterprisePaymentResponse>> callable = () ->
                EnterprisePayProcess.builder()
                        .context(EnterprisePayProcess.Context.builder()
                                .alipayClientFinder(merchantNo -> merchantManager.get(merchantNo).getClient())
                                .payeeFinder(merchantService::getChannel)
                                .saveOrder(entPayDao::save)
                                .saveRequest(entPayRequestDao::save)
                                .saveResponse(entPayResponseDao::save)
                                .saveThirdRequest(entPayThirdRequestDao::save)
                                .saveThirdResponse(entPayThirdResponseDao::save)
                                .saveError(entPayErrorDao::save)
                                .build())
                        .request(request)
                        .tradeNo(tradeNo)
                        .enterprisePaymentOrder(byOutTradeNo)
                        .messageProcessor(messageProcessor)
                        .build().process();

        Future<ResponseErrorCode<EnterprisePaymentResponse>> submit = executorService.submit(callable);

        try {
            ResponseErrorCode<EnterprisePaymentResponse> enterprisePaymentResponse = submit.get(500, TimeUnit.MILLISECONDS);
            if (StringUtils.isNotEmpty(enterprisePaymentResponse.getErrorCode())) {
                Optional.ofNullable(Errors.getErrors(enterprisePaymentResponse.getErrorCode())).orElse(Errors.PAYMENT_CALLING_FAILED)
                        .throwException(PaymentException.class, enterprisePaymentResponse.getErrorMessage());
            }
            return enterprisePaymentResponse.getResponse();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {

            return  new EnterprisePaymentResponse(){{
                setOutTradeNo(request.getOutTradeNo());
                setAmount(request.getAmount());
                setState(StatusType.PROCESSING.getCode());
                setTradeNo(tradeNo);
            }};
        }
    }

    @Override
    public EnterprisePayment queryEntPay(String no,  String merchantNo,String type) throws PaymentException {
        EnterprisePaymentOrder enterprisePaymentOrder;
        if ("out_Trade_No".equalsIgnoreCase(type)) {
            enterprisePaymentOrder = entPayDao.getByOutTradeNo(no);
        } else {
            enterprisePaymentOrder = entPayDao.getByTradeNo(no);
        }
        if (enterprisePaymentOrder == null) {
            Errors.PAYMENT_ORDER_NOT_EXISTS.throwException(PaymentException.class);
        } else if (enterprisePaymentOrder.getState().equals(StatusType.PROCESSING.getCode())) {
            AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
            AlipayFundTransOrderQueryModel alipayFundTransOrderQueryModel = new AlipayFundTransOrderQueryModel();
            alipayFundTransOrderQueryModel.setOutBizNo(no);
            request.setBizModel(alipayFundTransOrderQueryModel);
            try {
                AlipayFundTransOrderQueryResponse response = merchantManager.get(merchantNo).getClient().certificateExecute(request);
                if ("10000".equals(response.getCode())) {
                    enterprisePaymentOrder.setState(StatusType.SUCCESS.getCode().equals(response.getStatus())
                            ? StatusType.SUCCESS.getCode() : StatusType.FAILURE.getCode());
                    enterprisePaymentOrder.setPaymentNo(response.getOrderId());
                    Date parse = null;
                    try {
                        parse = new SimpleDateFormat(FORMAT_DATE_TIME_PATTEN).parse(response.getPayDate());
                    } catch (ParseException ignored) {
                    }
                    enterprisePaymentOrder.setPaymentTime(parse);
                    EnterprisePaymentOrder saveResult = entPayDao.save(enterprisePaymentOrder);
                    EnterprisePayment enterprisePayment = BeanUtils.mapping(saveResult, EnterprisePayment.class);
                    enterprisePayment.setBody(Objects.requireNonNull(enterprisePaymentOrder).getDescription());
                    return enterprisePayment;
                }
            } catch (AlipayApiException e) {
                Errors.PAYMENT_CALLING_FAILED.throwException(PaymentException.class);
            }
        }
        EnterprisePayment enterprisePayment = BeanUtils.mapping(enterprisePaymentOrder, EnterprisePayment.class);
        enterprisePayment.setBody(Objects.requireNonNull(enterprisePaymentOrder).getDescription());
        return enterprisePayment;
    }
}



