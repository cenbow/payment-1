/*
 * Project: Accounts
 * Document: PaymentAccountVo
 * Date: 2020/7/27 18:00
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.vo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author fengzl
 * @Date 2020/7/27
 */
@Data
@Builder
public class PaymentAccountVo {
    private String no;

    private BigDecimal balance;

    private BigDecimal availableBalance;

    private BigDecimal unusableBalance;

    private List<?> balanceDetails;
}
