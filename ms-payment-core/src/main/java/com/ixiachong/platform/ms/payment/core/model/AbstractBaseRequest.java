/*
 * Project: Accounts
 * Document: AbstractBaseRequest
 * Date: 2020/8/15 11:32
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.model;

import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import com.ixiachong.platform.ms.payment.core.model.trans.Withdraw;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @Author fengzl
 * @Date 2020/8/15
 */
@Setter
@Getter
@MappedSuperclass
public class AbstractBaseRequest extends AbstractCreatedEntity {
    /**
     * 商户系统生成的交易单号
     */
    @Column(name = "middle_out_trade_no", length = 36)
    private String middleOutTradeNo;
    /**
     * 客户端传的商户单号
     */
    @Column(name = "out_trade_no", length = 36)
    private String outTradeNo;
    /**
     * 平台分配的商户号
     */
    @Column(name = "merchant_no", length = 36)
    private String merchantNo;

    /**
     * 交易信息摘要，可以当作交易记录标题
     */
    @Column(name = "summary")
    private String summary;

    /**
     * 交易信息描述，可以作为交易详情描述
     */
    @Column(name = "body")
    private String body;

    @ManyToOne
    @JoinColumn(name = "withdraw")
    private Withdraw withdraw;
}
