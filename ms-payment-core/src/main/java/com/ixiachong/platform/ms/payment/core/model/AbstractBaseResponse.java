/*
 * Project: Accounts
 * Document: AbstractBaseResponse
 * Date: 2020/8/15 11:39
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.model;

import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import com.ixiachong.platform.commons.payment.beans.StatusType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

/**
 * @Author fengzl
 * @Date 2020/8/15
 */
@Setter
@Getter
@MappedSuperclass
public class AbstractBaseResponse extends AbstractCreatedEntity {
    /**
     * 商户系统生成的交易单号
     */
    @Column(name = "out_trade_no", length = 36)
    private String outTradeNo;

    /**
     * 账户提出的资金总额，此金额不等于最终用户入账金额，此金额包含手续费。
     */
    @Column(name = "amount", precision = 12, scale = 2)
    private BigDecimal amount;

    /**
     * 平台生成的唯一单号
     */
    @Column(name = "trade_no", length = 36)
    private String tradeNo;

    @Column(name = "status", length = 10)
    private StatusType status;

    @Column(name = "reason")
    private String reason;
}
