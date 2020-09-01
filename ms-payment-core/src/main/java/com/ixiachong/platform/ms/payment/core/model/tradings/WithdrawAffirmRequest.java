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

import com.ixiachong.platform.ms.payment.core.model.AbstractBaseRequest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Author fengzl
 * @Date 2020/8/15
 */
@Setter
@Getter
@Entity
@Table(name = "withdraw_affirm_request")
public class WithdrawAffirmRequest extends AbstractBaseRequest {
    /**
     * 交易信息摘要，可以当作交易记录标题
     */
    @Column(name = "summary")
    private String summary;

    /**
     * 提款发起方本地业务系统单号，以便于日后跟踪记录
     */
    @Column(name = "business_no", length = 36)
    private String businessNo;

    /**
     * 交易信息描述，可以作为交易详情描述
     */
    @Column(name = "body")
    private String body;

    /**
     * 提款冻结时系统返回的冻结单号
     */
    @Column(name = "freeze_no", length = 36)
    private String freezeNo;
}
