/**
 * Project: account-api
 * Document: EnterprisePaymentResponse
 * Date: 2020/8/5 10:33
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: wangcy
 */
@Data
public class EnterprisePaymentResponse {
    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 平台分配的商户号
     */
    private String tradeNo;

    /**金额*/
    private BigDecimal amount;

    /**
     * 订单支付状态
     */
    private String state;

}
