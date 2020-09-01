/**
 * Project: parent
 * Document: EnterprisePaymentThirdRequest
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
 * Author: wangcy
 */
@Data
@Entity
@Table(name = "enterprise_payment_third_request")
@EqualsAndHashCode(of = "id")
@ToString
public class EnterprisePaymentThirdRequest extends AbstractCreatedEntity {

    @Column(name = "open_id", length = 64)
    private String openId;

    @Column(name = "amount", scale = 2)
    private String amount;

    @Column(name = "check_name", length = 64)
    private String checkName;

    @Column(name = "spbill_create_ip", length = 32)
    private String spbillCreateIp;

    @Column(name = "partner_trade_no", length = 64)
    private String partnerTradeNo;

    @Column(name = "description", length = 255)
    private String description;

}
