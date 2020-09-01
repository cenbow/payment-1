/*
 * Project: Accounts
 * Document: ApplicationWithdrawMerchantDao
 * Date: 2020/8/14 17:06
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.dao;

import com.ixiachong.platform.ms.payment.core.model.ApplicationWithdrawMerchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author fengzl
 * @Date 2020/8/14
 */
public interface ApplicationWithdrawMerchantDao extends JpaRepository<ApplicationWithdrawMerchant, String> {
    @Query("select o.merchantNo from ApplicationWithdrawMerchant o where o.appId = ?1 and o.channel = ?2")
    String getMerchantNoByAppIdAAndChannel(String appId, String channel);
}
