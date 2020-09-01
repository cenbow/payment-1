/*
 * Project: Payment
 * Document: MessagingService
 * Date: 2020/8/26 6:29 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.notification.service;

import com.ixiachong.platform.commons.payment.notify.Notification;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Log
@Service
public class MessagingService {
    private NotificationService service;

    @Autowired
    public void setService(NotificationService service) {
        this.service = service;
    }

    @StreamListener(Sink.INPUT)
    public void receiveNotice(Message<Notification> message) {
        Notification notification = message.getPayload();
        log.info(String.format("接收到通知申请[%s,%s]", notification.getType(), notification.getIdentify()));
        try {
            service.startNotifyResult(notification.getType(), notification.getIdentify(), notification.getParams());
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
}
