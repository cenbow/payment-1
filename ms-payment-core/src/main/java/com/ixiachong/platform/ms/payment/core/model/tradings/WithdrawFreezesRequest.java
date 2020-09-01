/*
 * Project: Accounts
 * Document: WithdrawFreezeRequest
 * Date: 2020/8/15 10:07
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
@Table(name = "withdraw_freezes_request")
public class WithdrawFreezesRequest extends AbstractBaseRequest {
    /**
     * 需要提出资金的用户账户号
     */
    @Column(name = "account_no", length = 36)
    private String accountNo;

    /**
     * 提款发起方本地业务系统单号，以便于日后跟踪记录
     */
    @Column(name = "business_no", length = 36)
    private String businessNo;

    /**
     * 账户提出的资金总额，此金额不等于最终用户入账金额，此金额包含手续费。
     */
    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;
}
