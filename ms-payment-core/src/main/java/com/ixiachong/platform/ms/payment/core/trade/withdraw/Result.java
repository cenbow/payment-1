/*
 * Project: Payment
 * Document: Result
 * Date: 2020/8/10 11:41 上午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.withdraw;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private String tradeNo;
    private String transactionTime;
    private String outTradeNo;
    private String failureCode;
    private String failureMessage;
    private String status;
    private BigDecimal amount;
    private BigDecimal fee;
}
