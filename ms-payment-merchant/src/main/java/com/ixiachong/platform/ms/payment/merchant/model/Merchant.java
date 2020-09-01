/*
 * Project: Payment
 * Document: Merchant
 * Date: 2020/8/12 4:47 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.model;

import com.ixiachong.commons.jpa.AbstractEditedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "merchant")
public class Merchant extends AbstractEditedEntity {
    /**
     * 商户号
     */
    @Column(name = "no", nullable = false, unique = true, length = 36)
    private String no;
    /**
     * 客户编号
     */
    @Column(name = "customer_no", nullable = false, unique = true, length = 36)
    private String customerNo;
    /**
     * 账户号
     */
    @Column(name = "account_no", nullable = false, unique = true, length = 36)
    private String accountNo;
}
