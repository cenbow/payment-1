/*
 * Project: Accounts
 * Document: WithdrawChannelRequest
 * Date: 2020/8/15 14:45
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.model.tradings;

import com.ixiachong.platform.ms.payment.core.model.AbstractBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @Author fengzl
 * @Date 2020/8/15
 */
@Setter
@Getter
@Entity
@Table(name = "withdraw_channel_request")
public class WithdrawChannelRequest extends AbstractBaseRequest {
    @Column(name = "channel",length = 10)
    private String channel;

    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;
}
