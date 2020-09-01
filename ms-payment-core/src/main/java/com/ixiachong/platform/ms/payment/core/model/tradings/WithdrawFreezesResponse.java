/*
 * Project: Accounts
 * Document: WithdrawFreezeResponse
 * Date: 2020/8/15 11:36
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.model.tradings;

import com.ixiachong.platform.ms.payment.core.model.AbstractBaseResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Author fengzl
 * @Date 2020/8/15
 */
@Setter
@Getter
@Entity
@Table(name = "withdraw_freezes_response")
public class WithdrawFreezesResponse extends AbstractBaseResponse {
    /**
     * 冻结成功后返回的冻结单号
     */
    @Column(name = "freeze_no", length = 36)
    private String freezeNo;

    /**
     * 账户提出的资金总额，此金额不等于最终用户入账金额，此金额包含手续费。
     */
    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "request")
    private WithdrawFreezesRequest request;
}
