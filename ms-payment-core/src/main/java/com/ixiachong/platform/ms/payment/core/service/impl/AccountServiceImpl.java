/*
 * Project: Accounts
 * Document: AccountServiceImpl
 * Date: 2020/7/21 11:39
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.service.impl;

import com.ixiachong.commons.etc.exceptions.BaseRuntimeException;
import com.ixiachong.platform.account.api.vo.AccountBalanceVo;
import com.ixiachong.platform.account.api.vo.AccountSubjectBalanceVo;
import com.ixiachong.platform.account.api.vo.AccountVo;
import com.ixiachong.platform.ms.payment.core.Const;
import com.ixiachong.platform.ms.payment.core.service.AccountService;
import com.ixiachong.platform.ms.payment.core.vo.PaymentAccountVo;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author fengzl
 * @Date 2020/7/21
 */
@Log
@Service
public class AccountServiceImpl extends AbstractGenericService implements AccountService {
    @Override
    public PaymentAccountVo getAccount(String no) {
        List<AccountSubjectBalanceVo> balances = accountService.balance(no);
        AccountVo accountSummary = accountService.getAccountSummary(no);
        if (null == accountSummary) {
            throw new BaseRuntimeException("查询账户信息为null,账户号为：" + no, Const.FAILURE);
        }
        AccountBalanceVo accountBalance = accountSummary.getBalance();
        BigDecimal available = BigDecimal.ZERO,
                balance = BigDecimal.ZERO,
                unusable = BigDecimal.ZERO;
        if (null != accountBalance) {
            available = accountBalance.getAvailable();
            balance = accountBalance.getBalance();
            unusable = accountBalance.getUnusable();
        }
        return PaymentAccountVo.builder().balanceDetails(balances)
                .balance(balance)
                .availableBalance(available)
                .unusableBalance(unusable)
                .no(accountSummary.getNo()).build();
    }

}
