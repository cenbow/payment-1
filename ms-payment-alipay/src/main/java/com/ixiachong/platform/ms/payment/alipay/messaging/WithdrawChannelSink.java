/**
 * Project: parent
 * Document: WithdrawChannelSink
 * Date: 2020/8/27 14:57
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Author: wangcy
 */
public interface WithdrawChannelSink {

    String CHANNEL_INPUT = "withdraw-review-sink";

    @Input(CHANNEL_INPUT)
    SubscribableChannel withdrawChannel();



}
