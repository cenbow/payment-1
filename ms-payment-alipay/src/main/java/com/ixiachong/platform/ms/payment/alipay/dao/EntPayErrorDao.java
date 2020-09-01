package com.ixiachong.platform.ms.payment.alipay.dao;

import com.ixiachong.platform.ms.payment.alipay.model.EnterprisePaymentError;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project: parent
 * Document: EntPayErrorDao
 * Date: 2020/8/28 11:18
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public interface EntPayErrorDao  extends JpaRepository<EnterprisePaymentError, String> {
}
