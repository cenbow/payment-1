/*
 * Project: Accounts
 * Document: WithdrawReviewEvent
 * Date: 2020/8/26 10:51
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.event;

import lombok.Getter;

/**
 * @Author fengzl
 * @Date 2020/8/26
 */
@Getter
public class WithdrawReviewEvent<T> extends AbstractEvent {
    private final T message;

    public WithdrawReviewEvent(Object source, T message) {
        super(source);
        this.message = message;
    }
}
