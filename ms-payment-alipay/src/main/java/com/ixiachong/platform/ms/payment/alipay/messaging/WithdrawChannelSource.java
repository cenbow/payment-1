/*
 * Project: Accounts
 * Document: WithdrawChannelSource
 * Date: 2020/8/26 9:13
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @Author fengzl
 * @Date 2020/8/26
 */
public interface WithdrawChannelSource {
    String CHANNEL_OUTPUT = "withdraw-channel-source";

    @Output(CHANNEL_OUTPUT)
    MessageChannel outputChannel();
}
