/*
 * Project: Payment
 * Document: ApplicationWithdrawMerchant
 * Date: 2020/8/14 4:58 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.model;

import com.ixiachong.commons.jpa.AbstractEditedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity
@Table(name = "application_withdraw_merchant", uniqueConstraints = {@UniqueConstraint(columnNames = {"app_id", "channel"})})
public class ApplicationWithdrawMerchant extends AbstractEditedEntity {
    @Column(name = "app_id", length = 36, nullable = false)
    private String appId;

    @Column(name = "channel", length = 36, nullable = false)
    private String channel;

    @Column(name = "merchant_no", length = 36, nullable = false)
    private String merchantNo;
}
