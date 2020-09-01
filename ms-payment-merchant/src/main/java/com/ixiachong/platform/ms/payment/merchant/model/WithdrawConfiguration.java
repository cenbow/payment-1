/*
 * Project: Payment
 * Document: WithdrawConfiguration
 * Date: 2020/8/13 2:48 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.model;


import com.ixiachong.commons.jpa.AbstractConfigurationEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "withdraw_configuration")
public class WithdrawConfiguration extends AbstractConfigurationEntity {
    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private Merchant merchant;
}
