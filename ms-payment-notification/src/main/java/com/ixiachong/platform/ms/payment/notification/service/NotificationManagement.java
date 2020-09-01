/*
 * Project: Payment
 * Document: NotificationManagement
 * Date: 2020/8/26 6:28 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.notification.service;

import com.ixiachong.platform.ms.payment.notification.model.Notification;
import com.ixiachong.platform.ms.payment.notification.model.NotificationLog;

import java.util.List;

public interface NotificationManagement {
    Notification createNotification(Notification notification);

    Notification getNotification(String type, String identify);

    Notification getNotification(String id);

    NotificationLog createNotificationLog(String notificationId);

    void updateNotificationLog(NotificationLog notificationLog);

    List<Notification> findAllNotificationOfRequired();
}
