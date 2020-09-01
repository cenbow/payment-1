/*
 * Project: Accounts
 * Document: WithdrawQueryRequest
 * Date: 2020/8/24 18:22
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.withdraw;

import lombok.Data;

/**
 * @Author fengzl
 * @Date 2020/8/24
 */
@Data
public class WithdrawQueryRequest {
    private String tradeNo;
    private String outTradeNo;
}
