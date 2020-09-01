/*
 * Project: Accounts
 * Document: WithdrawCallBackRequest
 * Date: 2020/8/28 9:55
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.model.tradings;

import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author fengzl
 * @Date 2020/8/28
 */
@Setter
@Getter
@Entity
@Table(name = "withdraw_call_back_request")
public class WithdrawCallBackRequest extends AbstractCreatedEntity {
    @Column(name = "fail_code", length = 100)
    private String failCode;

    @Column(name = "reason")
    private String reason;

    @Column(name = "out_trade_no", length = 64)
    private String outTradeNo;

    @Column(name = "state", length = 20)
    private String state;
}
