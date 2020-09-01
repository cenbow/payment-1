/*
 * Project: Accounts
 * Document: AbstractEvent
 * Date: 2020/8/25 22:22
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

/**
 * @Author fengzl
 * @Date 2020/8/25
 */
@Getter
public class AbstractEvent extends ApplicationEvent {
    private final String id;

    public AbstractEvent(Object source) {
        super(source);
        this.id = UUID.randomUUID().toString();
    }
}
