/*
 * Project: Accounts
 * Document: WithdrawServiceImpl
 * Date: 2020/8/24 16:27
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.platform.account.api.request.WithdrawConfirmRequest;
import com.ixiachong.platform.account.api.response.WithdrawConfirmResponse;
import com.ixiachong.platform.commons.payment.beans.StatusType;
import com.ixiachong.platform.commons.payment.notify.Notification;
import com.ixiachong.platform.commons.payment.notify.NotificationParams;
import com.ixiachong.platform.ms.payment.core.Const;
import com.ixiachong.platform.ms.payment.core.exceptions.Errors;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.messaging.WithdrawNotifySource;
import com.ixiachong.platform.ms.payment.core.model.tradings.*;
import com.ixiachong.platform.ms.payment.core.model.trans.Withdraw;
import com.ixiachong.platform.ms.payment.core.service.WithdrawService;
import com.ixiachong.platform.ms.payment.core.trade.withdraw.AbstractService;
import com.ixiachong.platform.ms.payment.core.trade.withdraw.Result;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;

/**
 * @Author fengzl
 * @Date 2020/8/24
 */
@Service
@Log
@Transactional
public class WithdrawServiceImpl extends AbstractService implements WithdrawService {
    private WithdrawNotifySource notifySource;

    @Autowired
    public void setNotifySource(WithdrawNotifySource notifySource) {
        this.notifySource = notifySource;
    }

    @Override
    public void doCallBack(WithdrawCallBackRequest callbackRequest) throws TradeException {
        Withdraw withdraw = preprocess(callbackRequest);

        switch (callbackRequest.getState()) {
            case Const.SUCCESS_CODE:
                confirmWithdraw(withdraw);

                sendNotifyMessage(withdraw);
                break;
            case Const.FAILURE_CODE:
                revocationWithdraw(withdraw);
                break;
        }

        populateWithdraw(withdraw, callbackRequest);
        withdrawDao.save(withdraw);
    }

    private Withdraw preprocess(WithdrawCallBackRequest callbackRequest) throws TradeException {
        Optional<WithdrawChannelRequest> optional = channelRequestDao.findByMiddleOutTradeNo(callbackRequest.getOutTradeNo());
        if (optional.isEmpty() || !Arrays.asList(Const.SUCCESS_CODE, Const.FAILURE_CODE).contains(callbackRequest.getState())) {
            Errors.PARAMETERS_ERROR.throwException(TradeException.class);
        }

        WithdrawChannelRequest channelRequest = optional.get();
        Withdraw withdraw = channelRequest.getWithdraw();
        if (!withdraw.getStatus().name().equals(Const.PROCESSING)) {
            Errors.WITHDRAW_NOT_PROCESSING.throwException(TradeException.class);
        }
        return withdraw;
    }

    private void populateWithdraw(Withdraw withdraw, WithdrawCallBackRequest callBackRequest) {
        withdraw.setFailCode(callBackRequest.getFailCode());
        withdraw.setReason(callBackRequest.getReason());
        withdraw.setStatus(Const.STATUS.get(callBackRequest.getState()));
    }

    private void revocationWithdraw(Withdraw withdraw) {
        //TODO: revocation
    }

    private void confirmWithdraw(Withdraw withdraw) {
        Optional<WithdrawFreezesRequest> byWithdraw = freezeRequestDao.findByWithdraw(withdraw);
        if (byWithdraw.isPresent()) {
            Optional<WithdrawFreezesResponse> byRequest = freezeResponseDao.findByRequest(byWithdraw.get());
            if (byRequest.isPresent()) {
                WithdrawConfirmRequest confirmRequest = new WithdrawConfirmRequest();
                WithdrawAffirmRequest affirmRequest = saveAffirmRequest(confirmRequest, withdraw, byRequest.get());

                WithdrawConfirmResponse confirms = transactionService.confirms(confirmRequest);

                saveAffirmResponse(confirms, affirmRequest);
            }
        }
    }

    private WithdrawAffirmRequest saveAffirmRequest(WithdrawConfirmRequest confirmRequest, Withdraw withdraw, WithdrawFreezesResponse freezesResponse) {
        String no = applicationWithdrawMerchantDao.getMerchantNoByAppIdAAndChannel(withdraw.getAppId(), withdraw.getChannel());
        confirmRequest.setMerchantNo(no);
        confirmRequest.setSummary(withdraw.getSummary());
        confirmRequest.setBody(withdraw.getBody());
        confirmRequest.setFreezeNo(freezesResponse.getFreezeNo());
        confirmRequest.setOutTradeNo(newId().replaceAll("-", ""));
        confirmRequest.setBusinessNo(newId().replaceAll("-", ""));
        WithdrawAffirmRequest mapping = BeanUtils.mapping(confirmRequest, WithdrawAffirmRequest.class);
        mapping.setMiddleOutTradeNo(confirmRequest.getOutTradeNo());
        mapping.setOutTradeNo(withdraw.getOutTradeNo());
        mapping.setWithdraw(withdraw);
        return confirmRequestDao.save(setCreatedEntityProperties(mapping));
    }

    private void saveAffirmResponse(WithdrawConfirmResponse confirmResponse, WithdrawAffirmRequest affirmRequest) {
        WithdrawAffirmResponse affirmResponse = BeanUtils.mapping(confirmResponse, WithdrawAffirmResponse.class);
        affirmResponse.setRequest(affirmRequest);
        affirmResponse.setStatus(StatusType.SUCCESS);
        affirmResponse.setTradeNo(newId().replaceAll("-", ""));
        confirmResponseDao.save(setCreatedEntityProperties(affirmResponse));
    }

    public void sendNotifyMessage(Withdraw withdraw) {
        Notification notification = new Notification();
        notification.setIdentify(withdraw.getTradeNo());
        notification.setType(Const.TRADE_WITHDRAW);
        NotificationParams params = new NotificationParams();
        params.setAppId(withdraw.getAppId());
        params.setNotifyUrl(withdraw.getNotifyUrl());
        params.setAttach(withdraw.getAttach());
        params.setCode(withdraw.getStatus().name());
        params.setMessage(withdraw.getReason());
        Result result = toResult(withdraw);
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            json = "";
        }
        params.setBizContent(json);
        notification.setParams(params);
        notifySource.notifySource().send(MessageBuilder.withPayload(notification).build());
    }
}
