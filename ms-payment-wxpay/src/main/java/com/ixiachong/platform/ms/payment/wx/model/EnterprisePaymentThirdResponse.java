/**
 * Project: parent
 * Document: EnterprisePaymentThirdResponse
 * Date: 2020/8/28 10:16
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.model;

import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: wangcy
 */
@Data
@Entity
@Table(name = "enterprise_payment_third_response")
@EqualsAndHashCode(of = "id")
@ToString
public class EnterprisePaymentThirdResponse extends AbstractCreatedEntity {

    @Column(name = "partner_trade_no", length = 32)
    private String partnerTradeNo;

    @Column(name = "payment_no", length = 64)
    private String paymentNo;

    @Column(name = "payment_time", length = 32)
    private String paymentTime;


}
