/*
 * Project: Accounts
 * Document: WithdrawChannelProcessor
 * Date: 2020/8/26 9:29
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.service;

import com.ixiachong.platform.commons.payment.response.ResponseErrorCode;
import com.ixiachong.platform.ms.payment.core.dao.WithdrawCallBackRequestDao;
import com.ixiachong.platform.ms.payment.core.event.WithdrawChannelEvent;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.model.tradings.WithdrawCallBackRequest;
import com.ixiachong.platform.ms.payment.core.service.impl.AbstractGenericService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @Author fengzl
 * @Date 2020/8/26
 */
@Log
@Service
public class MessageProcessor extends AbstractGenericService {
    private WithdrawService withdrawService;
    private WithdrawCallBackRequestDao callBackRequestDao;

    @Autowired
    public void setCallBackRequestDao(WithdrawCallBackRequestDao callBackRequestDao) {
        this.callBackRequestDao = callBackRequestDao;
    }

    @Autowired
    public void setWithdrawService(WithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    @Async
    @EventListener
    public void process(@Nonnull WithdrawChannelEvent<ResponseErrorCode<Map<String, String>>> event) throws TradeException {
        ResponseErrorCode<Map<String, String>> message = event.getMessage();
        Map<String, String> response = message.getResponse();
        Assert.notNull(response, "消息参数为null");

        WithdrawCallBackRequest callbackRequest = new WithdrawCallBackRequest();
        callbackRequest.setFailCode(message.getErrorCode());
        callbackRequest.setReason(message.getErrorMessage());
        callbackRequest.setOutTradeNo(response.get("outTradeNo"));
        callbackRequest.setState(response.get("state"));
        callBackRequestDao.save(setCreatedEntityProperties(callbackRequest));

        withdrawService.doCallBack(callbackRequest);
    }
}
