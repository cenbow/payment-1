/*
 * Project: Accounts
 * Document: AbstractTrade
 * Date: 2020/7/25 11:09
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.model;

import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @Author fengzl
 * @Date 2020/7/25
 */
@Setter
@Getter
@MappedSuperclass
public class AbstractBaseTrade extends AbstractCreatedEntity {
    @Column(name = "app_id", length = 50)
    private String appId;

    @Column(name = "request_id", length = 50)
    private String requestId;

    @Column(name = "merchant_no", length = 36)
    private String merchantNo;

    @Column(name = "trade_no", length = 36)
    private String tradeNo;

    @Column(name = "out_trade_no", length = 36)
    private String outTradeNo;      //交易单号

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "accounting_time")
    private Date accountingTime;    //交易时间

    @Column(name = "timestamp", length = 14)
    private String timestamp;    //请求时间戳

    @Column(name = "attach", length = 100)
    private String attach;

    @Column(name = "notify_url")
    private String notifyUrl;
}
