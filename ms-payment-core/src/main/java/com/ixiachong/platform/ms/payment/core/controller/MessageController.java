/*
 * Project: Accounts
 * Document: MessageController
 * Date: 2020/8/29 17:38
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.controller;

import com.ixiachong.platform.ms.payment.core.messaging.WithdrawNotifySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author fengzl
 * @Date 2020/8/29
 */
@RestController
public class MessageController {
    @Autowired
    private WithdrawNotifySource notifySource;

    @GetMapping("/testMsg")
    public String testMessage() {
        notifySource.notifySource().send(MessageBuilder.withPayload("14234235").build());
        return "OK";
    }
}
