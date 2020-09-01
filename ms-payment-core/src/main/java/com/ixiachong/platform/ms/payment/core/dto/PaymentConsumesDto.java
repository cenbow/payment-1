/*
 * Project: Accounts
 * Document: InflowsDto
 * Date: 2020/7/22 18:19
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @Author fengzl
 * @Date 2020/7/22
 */

@Setter
@Getter
public class PaymentConsumesDto extends PaymentDto {
    @NotEmpty
    private String merchantNo;    //商户号

    @NotEmpty
    private String body; //对交易或商品的描述
}
