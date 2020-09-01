/**
 * Project: account-api
 * Document: EnterprisePaymentResponse
 * Date: 2020/8/5 10:33
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.model;

import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Author: wangcy
 */
@Data
@Entity
@Table(name = "enterprise_payment_response")
@EqualsAndHashCode(of = "id")
public class EnterprisePaymentResponse extends AbstractCreatedEntity {
    /**
     * 商户订单号
     */
    @Column(name = "out_trade_no", length = 64)
    private String outTradeNo;

    /**
     * 平台分配的商户号
     */
    @Column(name = "trade_no", length = 64)
    private String tradeNo;

    /**金额*/
    @Column(name = "amount", scale = 2)
    private BigDecimal amount;

    /**
     * 订单支付状态
     */
    @Column(name = "status", length = 12)
    private String status;

}
