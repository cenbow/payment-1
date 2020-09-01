/**
 * Project: account-api
 * Document: EnterprisePaymentRequest
 * Date: 2020/8/5 09:47
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.model;

import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author: wangcy
 */
@Data
@Entity
@Table(name = "enterprise_payment_request")
@EqualsAndHashCode(of = "id")
public class EnterprisePaymentRequest extends AbstractCreatedEntity {

    /**
     * 商户订单号
     */
    @Column(name = "out_trade_no", length = 50)
    private String outTradeNo;
    /**
     * 企业商户号（付款方）
     */
    @Column(name = "merchant_no", length = 32)
    private String merchantNo;
    /**
     * 收款方商户号
     */
    @Column(name = "payee_no", length = 32)
    private String payeeNo;
    /**
     * 付款金额
     */
    @Column(name = "amount", scale = 2)
    private BigDecimal amount;
    /**
     * 摘要（标题）
     */
    @Column(name = "summary", length = 64)
    private String summary;
    /**
     * 备注
     */
    @Column(name = "body", length = 255)
    private String body;
}
