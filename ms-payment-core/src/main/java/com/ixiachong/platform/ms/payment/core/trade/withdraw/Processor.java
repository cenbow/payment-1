/*
 * Project: Payment
 * Document: Handler
 * Date: 2020/8/7 6:02 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.withdraw;

import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.commons.etc.exceptions.Message;
import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import com.ixiachong.platform.account.api.request.WithdrawConfirmRequest;
import com.ixiachong.platform.account.api.request.WithdrawFreezeRequest;
import com.ixiachong.platform.account.api.response.WithdrawConfirmResponse;
import com.ixiachong.platform.account.api.response.WithdrawFreezeResponse;
import com.ixiachong.platform.commons.payment.beans.StatusType;
import com.ixiachong.platform.commons.payment.dto.Merchant;
import com.ixiachong.platform.commons.payment.request.EnterprisePaymentRequest;
import com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse;
import com.ixiachong.platform.commons.payment.service.AliPayService;
import com.ixiachong.platform.commons.payment.service.WithdrawChannels;
import com.ixiachong.platform.commons.payment.service.WxPayService;
import com.ixiachong.platform.ms.payment.core.Const;
import com.ixiachong.platform.ms.payment.core.exceptions.Errors;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.model.AbstractBaseRequest;
import com.ixiachong.platform.ms.payment.core.model.AbstractBaseResponse;
import com.ixiachong.platform.ms.payment.core.model.tradings.*;
import com.ixiachong.platform.ms.payment.core.model.trans.Withdraw;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

@Log
public class Processor {
    private final TradeRequest<BizContent> request;
    private final Context context;
    private BizContent bizContent;
    private Throwable throwable;
    private String middleMerchantNo; // 中间商户号(平台商户号)
    private Merchant userMerchant;  // 提现用户商户信息
    private WithdrawFreezeResponse freezes;
    private Withdraw withdraw;
    private String state;

    public Processor(Withdraw withdraw, TradeRequest<BizContent> request, Context context) {
        this.withdraw = withdraw;
        this.request = request;
        this.context = context;
    }

    private void initialize() throws TradeException {
        this.bizContent = request.getBizContent();
        if (null == bizContent
                || StringUtils.isEmpty(bizContent.getOutTradeNo())
                || StringUtils.isEmpty(bizContent.getMerchantNo())
                || StringUtils.isEmpty(bizContent.getChannel())
                || StringUtils.isEmpty(bizContent.getSummary())
                || StringUtils.isEmpty(bizContent.getBody())
                || null == bizContent.getAmount()) {
            Errors.REQUEST_BIZ_INCOMPLETE.throwException(TradeException.class);
        }

        userMerchant = context.getMerchantFinder().apply(request.getBizContent().getMerchantNo());
        if (userMerchant == null) {
            Errors.MERCHANT_NOT_EXISTS.throwException(TradeException.class);
        }

        middleMerchantNo = context.getApplicationMerchant().apply(request.getAppId(), bizContent.getChannel());
        if (StringUtils.isBlank(middleMerchantNo)) {
            Errors.WITHDRAW_APP_MERCHANT_NOT_EXISTS.throwException(TradeException.class);
        }
    }

    public Result process() {
        try {
            initialize(); // 初始化并校验数据有效性

            freezeAccounts(); // 冻结用户账户资金

            channelWithdraw(); // 发起提现

            freezeTransfer(); // 冻结转账
        } catch (Exception ex) {
            this.throwable = ex;
            log.log(Level.WARNING, ex.getMessage(), ex);
        } finally {
            saveResult(); // 记录交易结果
        }
        return getResult();
    }

    private void saveResult() {
        withdraw.setAccountingTime(new Date());
        withdraw.setUpdateTime(new Date());
        context.getSaveWithdraw().apply(withdraw);
    }

    private void throwTradeException(Exception ex) throws TradeException {
        log.log(Level.SEVERE, ex.getMessage(), ex);
        if (ex instanceof TradeException) {
            saveException(ex.getMessage(), ((TradeException) ex).getCode());
            throw (TradeException) ex;
        } else if (ex instanceof Message) {
            Message msg = (Message) ex;
            saveException(ex.getMessage(), ((Message) ex).getCode());
            throw new TradeException(msg.getMessage(), msg.getCode());
        } else {
            saveException(ex.getMessage(), Const.UNKNOWN);
            Errors.UNKNOWN.throwException(TradeException.class);
        }
    }

    private void saveException(String msg, String code) {
        withdraw.setStatus(StatusType.FAILURE);
        String message = msg.length() > 200 ? msg.substring(0, 200) : msg;
        withdraw.setReason(message);
        withdraw.setFailCode(code);
    }

    private void freezeTransfer() throws TradeException {
        if (!Const.SUCCESS.equals(state)) {
            return;
        }
        WithdrawConfirmRequest confirmRequest = BeanUtils.mapping(bizContent, WithdrawConfirmRequest.class);
        confirmRequest.setBusinessNo(context.getNoGenerator().get());
        confirmRequest.setMerchantNo(this.middleMerchantNo);
        confirmRequest.setOutTradeNo(context.getNoGenerator().get());
        confirmRequest.setFreezeNo(freezes.getFreezeNo());

        // 创建发送提现确认请求
        WithdrawAffirmRequest mapping = BeanUtils.mapping(confirmRequest, WithdrawAffirmRequest.class);
        setTradingRequest(mapping, confirmRequest.getOutTradeNo());
        context.getSaveWithdrawConfirmRequest().accept(setCreatedEntityProperties(mapping));

        WithdrawAffirmResponse confirmResponse = new WithdrawAffirmResponse();
        try {
            WithdrawConfirmResponse confirms = context.getTransactionService().confirms(confirmRequest);
            confirmResponse = BeanUtils.mapping(confirms, WithdrawAffirmResponse.class);
            confirmResponse.setStatus(StatusType.SUCCESS);

            withdraw.setStatus(StatusType.SUCCESS);
        } catch (Exception ex) {
            setExceptionMsg(confirmResponse, ex);
            throwTradeException(ex);
        } finally {
            // 保存提现确认结果
            confirmResponse.setBusinessNo(confirmRequest.getBusinessNo());
            confirmResponse.setOutTradeNo(bizContent.getOutTradeNo());
            confirmResponse.setTradeNo(context.getNoGenerator().get());
            confirmResponse.setRequest(mapping);
            context.getSaveWithdrawConfirmResponse().accept(setCreatedEntityProperties(confirmResponse));
        }
    }

    private void channelWithdraw() throws TradeException {
        String channel = bizContent.getChannel();
        try {
            switch (channel) {
                case Const.CHANNEL_WXPAY:
                    channelWithdraw(WxPayService.class);
                    break;
                case Const.CHANNEL_ALIPAY:
                    channelWithdraw(AliPayService.class);
                    break;
                default:
                    Errors.WITHDRAW_CHANNEL_NOT_EXISTS.throwException(TradeException.class);
            }
        } catch (TradeException ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
            throwTradeException(ex);
        }
    }

    private <T extends WithdrawChannels> void channelWithdraw(Class<T> clazz) throws TradeException {
        EnterprisePaymentRequest paymentRequest = BeanUtils.mapping(bizContent, EnterprisePaymentRequest.class);
        paymentRequest.setPayeeNo(bizContent.getMerchantNo());
        paymentRequest.setMerchantNo(this.middleMerchantNo);
        paymentRequest.setOutTradeNo(context.getNoGenerator().get());

        T service = context.getBeanFinder().findOne(clazz);
        // 记录发送提现的请求信息
        WithdrawChannelRequest mapping = BeanUtils.mapping(paymentRequest, WithdrawChannelRequest.class);
        setTradingRequest(mapping, paymentRequest.getOutTradeNo());
        mapping.setChannel(bizContent.getChannel());
        context.getSaveWithdrawChannelRequest().accept(setCreatedEntityProperties(mapping));

        WithdrawChannelResponse channelResponse = new WithdrawChannelResponse();
        try {
            EnterprisePaymentResponse paymentResponse = service.transfers(paymentRequest);

            state = paymentResponse.getState();
            withdraw.setStatus(Const.STATUS.get(state));

            org.springframework.beans.BeanUtils.copyProperties(paymentResponse, channelResponse);
            channelResponse.setStatus(Const.STATUS.get(state));

            if (paymentResponse.getState().equals(StatusType.FAILURE.getCode())) {
                Errors.WITHDRAW_FAILURE.throwException(TradeException.class);
            }
        } catch (Exception ex) {
            // 异常处理
            log.log(Level.WARNING, ex.getMessage(), ex);
            setExceptionMsg(channelResponse, ex);
            throwTradeException(ex);
        } finally {
            // 保存提现请求结果
            channelResponse.setAmount(paymentRequest.getAmount());
            channelResponse.setOutTradeNo(bizContent.getOutTradeNo());
            channelResponse.setTradeNo(context.getNoGenerator().get());
            channelResponse.setRequest(mapping);
            context.getSaveWithdrawChannelResponse().accept(setCreatedEntityProperties(channelResponse));
        }
    }

    private void freezeAccounts() throws TradeException {
        WithdrawFreezeRequest freezeRequest = BeanUtils.mapping(request.getBizContent(), WithdrawFreezeRequest.class);
        freezeRequest.setBusinessNo(context.getNoGenerator().get());
        freezeRequest.setMerchantNo(this.middleMerchantNo);
        freezeRequest.setOutTradeNo(context.getNoGenerator().get());
        freezeRequest.setAccountNo(userMerchant.getAccountNo());

        // 创建发送冻结请求
        WithdrawFreezesRequest mapping = BeanUtils.mapping(freezeRequest, WithdrawFreezesRequest.class);
        setTradingRequest(mapping, freezeRequest.getOutTradeNo());
        context.getSaveWithdrawFreezeRequest().accept(setCreatedEntityProperties(mapping));

        WithdrawFreezesResponse freezeResponse = new WithdrawFreezesResponse();
        try {
            freezes = context.getTransactionService().freezes(freezeRequest);
            freezeResponse = BeanUtils.mapping(freezes, WithdrawFreezesResponse.class);
            freezeResponse.setStatus(StatusType.SUCCESS);
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
            setExceptionMsg(freezeResponse, ex);
            throwTradeException(ex);
        } finally {
            freezeResponse.setOutTradeNo(bizContent.getOutTradeNo());
            freezeResponse.setAmount(freezeRequest.getAmount());
            freezeResponse.setTradeNo(context.getNoGenerator().get());
            freezeResponse.setRequest(mapping);
            // 保存冻结结果
            context.getSaveWithdrawFreezeResponse().accept(setCreatedEntityProperties(freezeResponse));
        }
    }

    private <T extends AbstractBaseRequest> void setTradingRequest(T t, String middleOutTradeNo) {
        t.setMiddleOutTradeNo(middleOutTradeNo);
        t.setOutTradeNo(bizContent.getOutTradeNo());
        t.setWithdraw(withdraw);
    }

    public Result getResult() {
        return Result.builder()
                .tradeNo(withdraw.getTradeNo())
                .outTradeNo(withdraw.getOutTradeNo())
                .transactionTime(new SimpleDateFormat("yyyyMMddHHmmss").format(withdraw.getAccountingTime()))
                .amount(withdraw.getAmount())
                .failureMessage(withdraw.getReason())
                .status(withdraw.getStatus().name())
                .failureCode(withdraw.getFailCode())
                .fee(withdraw.getFee())
                .build();
    }

    private <T extends AbstractCreatedEntity> T setCreatedEntityProperties(T entity) {
        entity.setId(context.getIdGenerator().get());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(entity.getCreateTime());
        return entity;
    }

    private <T extends AbstractBaseResponse> void setExceptionMsg(T t, Exception ex) {
        String msg = ex.getMessage().length() > 200 ? ex.getMessage().substring(0, 200) : ex.getMessage();
        t.setReason(msg);
        t.setStatus(StatusType.FAILURE);
    }
}
