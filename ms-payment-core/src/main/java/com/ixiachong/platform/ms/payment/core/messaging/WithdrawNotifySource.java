/*
 * Project: Accounts
 * Document: WithdrawNotifySource
 * Date: 2020/8/29 16:44
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Author fengzl
 * @Date 2020/8/29
 */
public interface WithdrawNotifySource {
    String NOTIFY_OUTPUT = "withdraw-notify-source";

    @Output(NOTIFY_OUTPUT)
    MessageChannel notifySource();
}
