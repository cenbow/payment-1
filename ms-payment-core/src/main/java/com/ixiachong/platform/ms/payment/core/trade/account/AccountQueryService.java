/*
 * Project: Accounts
 * Document: AccountQueryService
 * Date: 2020/8/20 18:06
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.account;

import com.ixiachong.platform.ms.payment.core.service.AccountService;
import com.ixiachong.platform.ms.payment.core.service.impl.AbstractGenericService;
import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import com.ixiachong.platform.ms.payment.core.vo.PaymentAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author fengzl
 * @Date 2020/8/20
 */
@Service
public class AccountQueryService extends AbstractGenericService implements TradeHandler<AccountQueryRequest, PaymentAccountVo> {
    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public PaymentAccountVo handle(TradeRequest<AccountQueryRequest> request) {

        return accountService.getAccount(request.getBizContent().getAccountNo());
    }
}
