/*
 * Project: Payment
 * Document: StreamConfigurer
 * Date: 2020/5/27 5:25 下午
 * Author: wangbz
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.config;

import com.ixiachong.platform.ms.payment.alipay.event.WithdrawReviewEvent;
import com.ixiachong.platform.ms.payment.alipay.messaging.WithdrawChannelSink;
import com.ixiachong.platform.ms.payment.alipay.messaging.WithdrawChannelSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Map;

/**
 * Cloud Stream 配置
 *
 * @author wangbz
 */
@Configuration
@EnableBinding({WithdrawChannelSink.class, WithdrawChannelSource.class})
public class StreamConfigurer {
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * 提现回调方法监听
     *
     * @param message
     */
    @StreamListener(WithdrawChannelSink.CHANNEL_INPUT)
    public void channelWithdraw(Message<Map<String, String>> message) {
        eventPublisher.publishEvent(new WithdrawReviewEvent<>(this, message.getPayload()));
    }

}
