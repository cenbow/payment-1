/*
 * Project: Accounts
 * Document: PaymentDto
 * Date: 2020/7/25 14:27
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author fengzl
 * @Date 2020/7/25
 */
@Data
public class PaymentDto {
    @NotEmpty
    private String outTradeNo;  //交易单号

    @NotEmpty
    private String summary;     //摘要
    @NotNull
    @DecimalMin(value = "0")
    private String amount;    //金额

    private String businessType;    //业务类型

    private String businessNo; //业务单号
}
