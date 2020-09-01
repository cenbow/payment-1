/*
 * Project: Accounts
 * Document: AbstractService
 * Date: 2020/8/20 21:16
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.withdraw;

import com.ixiachong.platform.commons.payment.service.MerchantService;
import com.ixiachong.platform.commons.payment.service.TransactionService;
import com.ixiachong.platform.ms.payment.core.dao.*;
import com.ixiachong.platform.ms.payment.core.model.trans.Withdraw;
import com.ixiachong.platform.ms.payment.core.service.impl.AbstractGenericService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

/**
 * @Author fengzl
 * @Date 2020/8/20
 */
public class AbstractService extends AbstractGenericService {
    protected TransactionService transactionService;
    protected MerchantService merchantService;
    protected WithdrawDao withdrawDao;
    protected ApplicationWithdrawMerchantDao applicationWithdrawMerchantDao;
    protected WithdrawFreezesRequestDao freezeRequestDao;
    protected WithdrawFreezesResponseDao freezeResponseDao;
    protected WithdrawChannelRequestDao channelRequestDao;
    protected WithdrawChannelResponseDao channelResponseDao;
    protected WithdrawAffirmRequestDao confirmRequestDao;
    protected WithdrawAffirmResponseDao confirmResponseDao;

    @Autowired
    public void setChannelRequestDao(WithdrawChannelRequestDao channelRequestDao) {
        this.channelRequestDao = channelRequestDao;
    }

    @Autowired
    public void setChannelResponseDao(WithdrawChannelResponseDao channelResponseDao) {
        this.channelResponseDao = channelResponseDao;
    }

    @Autowired
    public void setConfirmRequestDao(WithdrawAffirmRequestDao confirmRequestDao) {
        this.confirmRequestDao = confirmRequestDao;
    }

    @Autowired
    public void setConfirmResponseDao(WithdrawAffirmResponseDao confirmResponseDao) {
        this.confirmResponseDao = confirmResponseDao;
    }

    @Autowired
    public void setFreezeRequestDao(WithdrawFreezesRequestDao freezeRequestDao) {
        this.freezeRequestDao = freezeRequestDao;
    }

    @Autowired
    public void setFreezeResponseDao(WithdrawFreezesResponseDao freezeResponseDao) {
        this.freezeResponseDao = freezeResponseDao;
    }

    @Autowired
    public void setWithdrawDao(WithdrawDao withdrawDao) {
        this.withdrawDao = withdrawDao;
    }

    @Autowired
    public void setApplicationWithdrawMerchantDao(ApplicationWithdrawMerchantDao applicationWithdrawMerchantDao) {
        this.applicationWithdrawMerchantDao = applicationWithdrawMerchantDao;
    }

    @Autowired
    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    protected Result toResult(Withdraw withdraw) {
        return Result.builder()
                .tradeNo(withdraw.getTradeNo())
                .outTradeNo(withdraw.getOutTradeNo())
                .transactionTime(new SimpleDateFormat("yyyyMMddHHmmss").format(withdraw.getAccountingTime()))
                .amount(withdraw.getAmount())
                .status(withdraw.getStatus().name())
                .failureCode(withdraw.getFailCode())
                .failureMessage(withdraw.getReason())
                .fee(withdraw.getFee())
                .build();
    }
}
