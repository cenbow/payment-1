/*
 * Project: Accounts
 * Document: WithdrawCallBackRequest
 * Date: 2020/8/28 10:02
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.dao;

import com.ixiachong.platform.ms.payment.core.model.tradings.WithdrawCallBackRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author fengzl
 * @Date 2020/8/28
 */
public interface WithdrawCallBackRequestDao extends JpaRepository<WithdrawCallBackRequest, String> {
}
