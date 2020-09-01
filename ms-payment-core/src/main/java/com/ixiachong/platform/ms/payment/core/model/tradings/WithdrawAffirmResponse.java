/*
 * Project: Accounts
 * Document: WithdrawConfirmRequest
 * Date: 2020/8/15 14:27
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
@Table(name = "withdraw_affirm_response")
public class WithdrawAffirmResponse extends AbstractBaseResponse {
    /**
     * 提款冻结时发起的业务单号
     */
    @Column(name = "business_no", length = 36)
    private String businessNo;

    @OneToOne
    @JoinColumn(name = "request")
    private WithdrawAffirmRequest request;
}
