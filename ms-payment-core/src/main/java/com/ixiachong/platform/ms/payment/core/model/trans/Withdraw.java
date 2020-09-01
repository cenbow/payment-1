/*
 * Project: Accounts
 * Document: WithdrawTrade
 * Date: 2020/7/31 10:34
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.model.trans;

import com.ixiachong.platform.commons.payment.beans.StatusType;
import com.ixiachong.platform.ms.payment.core.model.AbstractDescription;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

/**
 * @Author fengzl
 * @Date 2020/7/31
 */
@Setter
@Getter
@Entity
@Table(name = "withdraw", uniqueConstraints =  {@UniqueConstraint(columnNames={"app_id", "out_trade_no"})})
public class Withdraw extends AbstractDescription {

    @Column(name = "channel", length = 10)
    private String channel;

    @Column(name = "fee", precision = 12, scale = 2)
    private BigDecimal fee;

    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "status", length = 10)
    private StatusType status;

    @Column(name = "fail_code", length = 100)
    private String failCode;

    @Column(name = "reason")
    private String reason;
}
