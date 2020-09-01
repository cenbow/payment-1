/*
 * Project: Accounts
 * Document: WithdrawFreezeRequest
 * Date: 2020/8/15 11:53
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.dao;

import com.ixiachong.platform.ms.payment.core.model.tradings.WithdrawFreezesRequest;
import com.ixiachong.platform.ms.payment.core.model.trans.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author fengzl
 * @Date 2020/8/15
 */
public interface WithdrawFreezesRequestDao extends JpaRepository<WithdrawFreezesRequest, String> {
    Optional<WithdrawFreezesRequest> findByWithdraw(Withdraw withdraw);
}
