/**
 * Project: parent
 * Document: EnterprisePaymentThirdResponse
 * Date: 2020/8/28 10:16
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
@Table(name = "enterprise_payment_third_response")
@EqualsAndHashCode(of = "id")
@ToString
public class EnterprisePaymentThirdResponse extends AbstractCreatedEntity {

    @Column(name = "out_biz_no", length = 64)
    private String outBizNo;

    @Column(name = "order_id", length = 32)
    private String orderId;

    @Column(name = "status", length = 32)
    private String status;

    @Column(name = "trans_date", length = 32)
    private String transDate;

}
