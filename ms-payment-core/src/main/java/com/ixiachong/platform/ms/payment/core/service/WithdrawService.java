/*
 * Project: Accounts
 * Document: WithdrawService
 * Date: 2020/8/24 16:25
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.service;

import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.model.tradings.WithdrawCallBackRequest;

/**
 * @Author fengzl
 * @Date 2020/8/24
 */
public interface WithdrawService {
    void doCallBack(WithdrawCallBackRequest callbackRequest) throws TradeException;
}
