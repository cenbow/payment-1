/**
 * Project: account-api
 * Document: EnterprisePaymentRequest
 * Date: 2020/8/5 09:47
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.request;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: wangcy
 */
@Data
public class EnterprisePaymentRequest {

    /**
     * 商户订单号
     */
    @NotNull
    private String outTradeNo;
    /**
     * 企业商户号（付款方）
     */
    private String merchantNo;
    /**
     * 收款方商户号
     */
    @NotNull
    private String payeeNo;
    /**
     * 付款金额
     */
    @DecimalMin(value = "0")
    @DecimalMax(value = "9999999999.99")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;
    /**
     * 摘要（标题）
     */
    private String summary;
    /**
     * 备注
     */
    @NotNull
    private String body;
}
