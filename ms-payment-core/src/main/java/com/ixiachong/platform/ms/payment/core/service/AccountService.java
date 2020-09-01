/*
 * Project: Accounts
 * Document: AccountService
 * Date: 2020/7/21 11:38
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.service;

import com.ixiachong.platform.ms.payment.core.vo.PaymentAccountVo;

/**
 * @Author fengzl
 * @Date 2020/7/21
 */
public interface AccountService {
    PaymentAccountVo getAccount(String no);
}
