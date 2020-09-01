/*
 * Project: Payment
 * Document: GeneralAmountBizContent
 * Date: 2020/8/8 11:04 上午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public abstract class GeneralAmountBizContent extends GeneralTradeBizContent {
    private BigDecimal amount;
}
