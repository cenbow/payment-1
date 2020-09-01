/**
 * Project: parent
 * Document: TransfersServiceImpl
 * Date: 2020/8/4 14:41
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.service.impl;

import com.github.binarywang.wxpay.bean.entpay.EntPayQueryResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.platform.commons.payment.beans.StatusType;
import com.ixiachong.platform.commons.payment.request.EnterprisePaymentRequest;
import com.ixiachong.platform.commons.payment.response.EnterprisePayment;
import com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse;
import com.ixiachong.platform.commons.payment.response.ResponseErrorCode;
import com.ixiachong.platform.ms.payment.wx.exceptions.Errors;
import com.ixiachong.platform.ms.payment.wx.exceptions.PaymentException;
import com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentOrder;
import com.ixiachong.platform.ms.payment.wx.service.EntPayService;
import com.ixiachong.platform.ms.payment.wx.service.MessageProcessor;
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
import java.util.concurrent.*;

/**
 * @Author: wangcy
 */
@Service
@Log4j2
public class EntPayServiceImpl extends AbstractService implements EntPayService {

    @Resource(name = "executorService")
    ExecutorService executorService;

    @Autowired
    private MessageProcessor messageProcessor;

    @Override
    public EnterprisePaymentResponse transfers(EnterprisePaymentRequest request) throws PaymentException {
        EnterprisePaymentOrder byOutTradeNo = entPayDao.getByOutTradeNo(request.getOutTradeNo());
        String tradeNo = byOutTradeNo == null ? UUID.randomUUID().toString() : byOutTradeNo.getTradeNo();
        Callable<ResponseErrorCode<EnterprisePaymentResponse>> call = () -> EnterprisePayProcess.builder()
                .context(EnterprisePayProcess.Context.builder()
                        .wxClientFinder(merchantNo -> merchantManager.get(merchantNo).getClient())
                        .payeeFinder(merchantService::getChannel)
                        .saveOrder(entPayDao::save)
                        .saveRequest(entPayRequestDao::save)
                        .saveResponse(entPayResponseDao::save)
                        .saveThirdRequest(entPayThirdRequestDao::save)
                        .saveThirdResponse(entPayThirdResponseDao::save)
                        .saveError(entPayErrorDao::save)
                        .build())
                .request(request)
                .enterprisePaymentOrder(byOutTradeNo)
                .tradeNo(tradeNo)
                .messageProcessor(messageProcessor)
                .build().process();

        Future<ResponseErrorCode<EnterprisePaymentResponse>> result = executorService.submit(call);

        try {
            ResponseErrorCode<EnterprisePaymentResponse> enterprisePaymentResponse = result.get(500, TimeUnit.MILLISECONDS);
            if (StringUtils.isNotEmpty(enterprisePaymentResponse.getErrorCode())) {
                Optional.ofNullable(Errors.getErrors(enterprisePaymentResponse.getErrorCode())).orElse(Errors.PAYMENT_CALLING_FAILED)
                        .throwException(PaymentException.class, enterprisePaymentResponse.getErrorMessage());
            }
            return enterprisePaymentResponse.getResponse();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            EnterprisePaymentResponse enterprisePaymentResponse = new EnterprisePaymentResponse();
            enterprisePaymentResponse.setOutTradeNo(request.getOutTradeNo());
            enterprisePaymentResponse.setAmount(request.getAmount());
            enterprisePaymentResponse.setState(StatusType.PROCESSING.getCode());
            enterprisePaymentResponse.setTradeNo(tradeNo);
            return enterprisePaymentResponse;
        }
    }


    @Override
    public EnterprisePayment queryEntPay(String no, String merchantNo, String type) throws PaymentException {
        EnterprisePaymentOrder enterprisePaymentOrder;
        if ("out_Trade_No".equalsIgnoreCase(type)) {
            enterprisePaymentOrder = entPayDao.getByOutTradeNo(no);
        } else {
            enterprisePaymentOrder = entPayDao.getByTradeNo(no);
        }
        if (enterprisePaymentOrder == null) {
            Errors.PAYMENT_ORDER_NOT_EXISTS.throwException(PaymentException.class);
        } else if (enterprisePaymentOrder.getState().equals(StatusType.PROCESSING.getCode())) {
            try {
                EntPayQueryResult entPayQueryResult = merchantManager.get(merchantNo).getClient().getEntPayService().queryEntPay(enterprisePaymentOrder.getOutTradeNo());

                enterprisePaymentOrder.setState(entPayQueryResult.getStatus());
                enterprisePaymentOrder.setPaymentNo(entPayQueryResult.getDetailId());
                Date parse = null;
                try {
                    parse = new SimpleDateFormat(FORMAT_DATE_TIME_PATTEN).parse(entPayQueryResult.getPaymentTime());
                } catch (ParseException ignored) {
                }
                enterprisePaymentOrder.setPaymentTime(parse);
                EnterprisePaymentOrder saveResult = entPayDao.save(enterprisePaymentOrder);
                EnterprisePayment enterprisePayment = BeanUtils.mapping(saveResult, EnterprisePayment.class);
                enterprisePayment.setBody(Objects.requireNonNull(enterprisePaymentOrder).getDescription());
                return enterprisePayment;
            } catch (WxPayException e) {
                log.info("=================>微信调用异常:{}", e.getErrCodeDes());
                Errors.PAYMENT_CALLING_FAILED.throwException(PaymentException.class, e.getReturnMsg());
            }
        }
        EnterprisePayment enterprisePayment = BeanUtils.mapping(enterprisePaymentOrder, EnterprisePayment.class);
        enterprisePayment.setBody(Objects.requireNonNull(enterprisePaymentOrder).getDescription());
        return enterprisePayment;
    }
}



