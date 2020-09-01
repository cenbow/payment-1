/*
 * Project: Payment
 * Document: NotificationService
 * Date: 2020/8/26 6:28 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.notification.service;

import com.ixiachong.platform.commons.payment.notify.NotificationParams;
import com.ixiachong.platform.ms.payment.notification.model.Notification;

public interface NotificationService {
    Notification startNotifyResult(String type, String identify, NotificationParams params);

    void notify(String notificationId);
}
