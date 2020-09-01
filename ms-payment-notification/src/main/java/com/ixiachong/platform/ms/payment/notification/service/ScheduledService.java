/*
 * Project: Payment
 * Document: ScheduledService
 * Date: 2020/8/27 3:44 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.notification.service;

import com.ixiachong.platform.ms.payment.notification.model.Notification;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Log
@Service
public class ScheduledService {
    private NotificationService notificationService;
    private NotificationManagement notificationManagement;

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Autowired
    public void setNotificationManagement(NotificationManagement notificationManagement) {
        this.notificationManagement = notificationManagement;
    }

    @Scheduled(cron = "0 0/3 * * * *")
    public void notifySchedule() {
        log.info("通知计划执行.");

        List<Notification> list = notificationManagement.findAllNotificationOfRequired();
        if (list == null || list.isEmpty()) {
            log.info("没有需要执行的通知.");
            return;
        }
        list.forEach(notification -> notificationService.notify(notification.getId()));
    }
}
