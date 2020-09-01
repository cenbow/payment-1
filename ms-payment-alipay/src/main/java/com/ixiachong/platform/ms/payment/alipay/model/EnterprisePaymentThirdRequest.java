/**
 * Project: parent
 * Document: EnterprisePaymentThirdRequest
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
 * Author: wangcy
 */
@Data
@Entity
@Table(name = "enterprise_payment_third_request")
@EqualsAndHashCode(of = "id")
@ToString
public class EnterprisePaymentThirdRequest extends AbstractCreatedEntity {

    @Column(name = "out_biz_no", length = 64)
    private String outBizNo;

    @Column(name = "trans_amount", length = 20)
    private String transAmount;

    @Column(name = "product_code", length = 64)
    private String productCode;

    @Column(name = "identity", length = 64)
    private String identity;

    @Column(name = "identity_type", length = 64)
    private String identityType;

}
