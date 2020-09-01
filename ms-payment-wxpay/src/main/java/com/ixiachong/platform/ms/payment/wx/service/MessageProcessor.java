/*
 * Project: Accounts
 * Document: SendMessageService
 * Date: 2020/8/25 18:01
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.service;

import com.github.binarywang.wxpay.bean.entpay.EntPayQueryResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.platform.commons.payment.beans.StatusType;
import com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse;
import com.ixiachong.platform.commons.payment.response.ResponseErrorCode;
import com.ixiachong.platform.ms.payment.wx.event.WithdrawReviewEvent;
import com.ixiachong.platform.ms.payment.wx.exceptions.Errors;
import com.ixiachong.platform.ms.payment.wx.exceptions.PaymentException;
import com.ixiachong.platform.ms.payment.wx.messaging.WithdrawChannelSource;
import com.ixiachong.platform.ms.payment.wx.model.EnterprisePaymentOrder;
import com.ixiachong.platform.ms.payment.wx.service.impl.AbstractService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @Author fengzl
 * @Date 2020/8/25
 */
@Service
public class MessageProcessor extends AbstractService {
    private WithdrawChannelSource channelSource;

    @Autowired
    public void setChannelSource(WithdrawChannelSource channelSource) {
        this.channelSource = channelSource;
    }

    public void sendMessage(Object object) {
        channelSource.outputChannel().send(MessageBuilder.withPayload(object).build());
    }

    @Async
    @EventListener
    public void process(WithdrawReviewEvent<Map<String, String>> event) throws PaymentException {
        Map<String, String> message = event.getMessage();
        String outTradeNo = message.get("outTradeNo");
        if (StringUtils.isEmpty(outTradeNo)) {
            return;
        }
        EnterprisePaymentOrder enterprisePaymentOrder = entPayDao.getByOutTradeNo(outTradeNo);
        if (enterprisePaymentOrder == null) {
            return;
        }
        if (!enterprisePaymentOrder.getState().equals(StatusType.PROCESSING.getCode())) {
            sendMessage(new ResponseErrorCode<>() {{
                setResponse(BeanUtils.mapping(enterprisePaymentOrder, EnterprisePaymentResponse.class));
            }});
            return;
        }
        try {
            EntPayQueryResult entPayQueryResult = merchantManager.get(enterprisePaymentOrder.getMerchantNo()).getClient().getEntPayService().queryEntPay(outTradeNo);
            if (!enterprisePaymentOrder.getState().equals(entPayQueryResult.getStatus())) {
                enterprisePaymentOrder.setState(entPayQueryResult.getStatus());
                entPayDao.save(enterprisePaymentOrder);
            }
            sendMessage(new ResponseErrorCode<>() {{
                setResponse(BeanUtils.mapping(enterprisePaymentOrder, EnterprisePaymentResponse.class));
            }});
        } catch (WxPayException e) {
            Optional.ofNullable(Errors.getErrors(e.getErrCode())).orElse(Errors.PAYMENT_CALLING_FAILED)
                    .throwException(PaymentException.class, e.getErrCodeDes());
        }
    }
}
