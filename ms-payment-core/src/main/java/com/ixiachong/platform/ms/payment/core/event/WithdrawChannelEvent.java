/*
 * Project: Accounts
 * Document: WithdrawChannel
 * Date: 2020/8/25 22:23
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.event;

import lombok.Getter;

/**
 * @Author fengzl
 * @Date 2020/8/25
 */
@Getter
public class WithdrawChannelEvent<T> extends AbstractEvent {
    private final T message;

    public WithdrawChannelEvent(Object source, T message) {
        super(source);
        this.message = message;
    }
}
