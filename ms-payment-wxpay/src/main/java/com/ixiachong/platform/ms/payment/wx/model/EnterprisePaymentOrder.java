/*
  Project: parent
  Document: EnterprisePaymentOrder
  Date: 2020/8/5 15:30
  Author: wangcy
  Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
  注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */

package com.ixiachong.platform.ms.payment.wx.model;

import com.ixiachong.commons.jpa.AbstractEditedEntity;
import com.ixiachong.platform.commons.payment.beans.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 企业支付订单流水
 */
@Data
@Entity
@Table(name = "enterprise_payment_orders")
@EqualsAndHashCode(of = "id")
@ToString
public class EnterprisePaymentOrder extends AbstractEditedEntity {

    /**
     * 收款用户openid
     */
    @Column(name = "open_id", length = 64)
    private String openId;
    /**
     * 平台分配的商户号
     */
    @Column(name = "trade_no", length = 64)
    private String tradeNo;
    /**
     * 商户订单号
     */
    @Column(name = "out_trade_no", unique = true, length = 50)
    private String outTradeNo;
    /**
     * 商户号
     */
    @Column(name = "merchant_no", length = 50)
    private String merchantNo;
    /**
     * 金额
     */
    @Column(name = "amount", scale = 2)
    private BigDecimal amount;
    @Column(name = "summary", length = 225)
    private String summary;
    /**
     * 企业付款备注
     */
    @Column(name = "description", length = 225)
    private String description;
    /**
     * 发起转账时间
     */
    @Column(name = "transfer_time", length = 225)
    private Date transferTime;
    /**
     * 微信付款单号
     */
    @Column(name = "payment_no", length = 50)
    private String paymentNo;
    /**
     * 付款成功时间
     */
    @Column(name = "payment_time")
    private Date paymentTime;
    /**
     * 失败原因
     */
    @Column(name = "reason")
    private String reason;

}