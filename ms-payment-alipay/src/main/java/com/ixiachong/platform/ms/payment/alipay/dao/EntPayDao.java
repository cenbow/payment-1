/*
 * Project: Accounts
 * Document: AccountBalanceDao
 * Date: 2020/8/5 19:19
 * Author: wangcy
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.dao;

import com.ixiachong.platform.ms.payment.alipay.model.EnterprisePaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 账户信息 dao层
 *
 * @author wangcy
 */
public interface EntPayDao extends JpaRepository<EnterprisePaymentOrder, String> {

    public EnterprisePaymentOrder getByOutTradeNo(String no);

    public EnterprisePaymentOrder getByTradeNo(String no);

}
