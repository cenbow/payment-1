/*
 * Project: Accounts
 * Document: SendMessageService
 * Date: 2020/8/25 18:01
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.platform.commons.payment.beans.StatusType;
import com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse;
import com.ixiachong.platform.commons.payment.response.ResponseErrorCode;
import com.ixiachong.platform.ms.payment.alipay.event.WithdrawReviewEvent;
import com.ixiachong.platform.ms.payment.alipay.exceptions.Errors;
import com.ixiachong.platform.ms.payment.alipay.exceptions.PaymentException;
import com.ixiachong.platform.ms.payment.alipay.messaging.WithdrawChannelSource;
import com.ixiachong.platform.ms.payment.alipay.model.EnterprisePaymentOrder;
import com.ixiachong.platform.ms.payment.alipay.service.impl.AbstractService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author fengzl
 * @Date 2020/8/25
 */
@Service
@EnableBinding({WithdrawChannelSource.class})
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
        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
        AlipayFundTransOrderQueryModel alipayFundTransOrderQueryModel = new AlipayFundTransOrderQueryModel();
        alipayFundTransOrderQueryModel.setOutBizNo(outTradeNo);
        request.setBizModel(alipayFundTransOrderQueryModel);
        try {
            AlipayFundTransOrderQueryResponse response = merchantManager.get(enterprisePaymentOrder.getMerchantNo()).getClient().certificateExecute(request);
            if (!enterprisePaymentOrder.getState().equals(response.getStatus())) {
                enterprisePaymentOrder.setState(response.getStatus());
                entPayDao.save(enterprisePaymentOrder);
            }
            sendMessage(new ResponseErrorCode<>() {{
                setResponse(BeanUtils.mapping(enterprisePaymentOrder, EnterprisePaymentResponse.class));
            }});
        } catch (AlipayApiException e) {
            Errors.PAYMENT_CALLING_FAILED.throwException(PaymentException.class);
        }
    }

}
