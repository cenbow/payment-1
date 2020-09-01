/*
 * Project: Accounts
 * Document: AbstractMessageTrade
 * Date: 2020/8/10 11:44
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @Author fengzl
 * @Date 2020/8/10
 */
@Setter
@Getter
@MappedSuperclass
public class AbstractDescription extends AbstractBaseTrade {
    @Column(name = "summary")
    private String summary;         //入账摘要

    @Column(name = "body")
    private String body;
}
