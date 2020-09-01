/**
 * Project: parent
 * Document: EnterprisePaymentError
 * Date: 2020/8/28 10:47
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.model;

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
@Table(name = "enterprise_payment_error")
@EqualsAndHashCode(of = "id")
@ToString
public class EnterprisePaymentError extends AbstractCreatedEntity {

    @Column(name = "out_trade_no", length = 50)
    private String outTradeNo;

    @Column(name = "error_code",  length = 32)
    private String errorCode;

    @Column(name = "error_message", length = 255)
    private String errorMessage;


}
