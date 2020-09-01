/*
 * Project: Accounts
 * Document: TradeRecordDao
 * Date: 2020/7/23 10:28
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.dao;

import com.ixiachong.platform.ms.payment.core.model.trans.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author fengzl
 * @Date 2020/7/23
 */
public interface WithdrawDao extends JpaRepository<Withdraw, String> {
    Optional<Withdraw> findByRequestId(String requestId);

    Optional<Withdraw> findByOutTradeNoAndAppId(String outTradeNo, String appId);

    Optional<Withdraw> findByTradeNo(String tradeNo);
}
