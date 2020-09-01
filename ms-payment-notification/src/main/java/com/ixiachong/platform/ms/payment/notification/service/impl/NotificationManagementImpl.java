/*
 * Project: Payment
 * Document: NotificationManagementImpl
 * Date: 2020/8/27 3:17 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.notification.service.impl;


import com.ixiachong.platform.commons.payment.util.ExpireTime;
import com.ixiachong.platform.ms.payment.notification.Const;
import com.ixiachong.platform.ms.payment.notification.dao.NotificationDao;
import com.ixiachong.platform.ms.payment.notification.dao.NotificationLogDao;
import com.ixiachong.platform.ms.payment.notification.model.Notification;
import com.ixiachong.platform.ms.payment.notification.model.NotificationLog;
import com.ixiachong.platform.ms.payment.notification.service.NotificationManagement;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NotificationManagementImpl implements NotificationManagement {
    @Resource
    private NotificationDao notificationDao;
    @Resource
    private NotificationLogDao notificationLogDao;
    private final ExpireTime[] times = new ExpireTime[]{
            new ExpireTime("15s"),
            new ExpireTime("30s"),
            new ExpireTime("5m"),
            new ExpireTime("5m"),
            new ExpireTime("10m"),
            new ExpireTime("10m"),
            new ExpireTime("1h"),
            new ExpireTime("2h"),
            new ExpireTime("6h")
    };

    @Override
    public Notification createNotification(Notification notification) {
        notification.setId(UUID.randomUUID().toString());
        notification.setState("N");
        notification.setCount(0);
        notification.setCreateTime(new Date());
        notification.setUpdateTime(notification.getCreateTime());
        return notificationDao.saveAndFlush(notification);
    }

    @Override
    public Notification getNotification(String type, String identify) {
        return notificationDao.getFirstByTypeAndIdentifyAndStateNotIn(type, identify, Arrays.asList("F"));
    }

    @Override
    public Notification getNotification(String id) {
        return notificationDao.getOne(id);
    }

    @Override
    public NotificationLog createNotificationLog(String notificationId) {
        Optional<Notification> optional = notificationDao.findById(notificationId);
        if (optional.isEmpty()) {
            return null;
        }
        Notification notification = optional.get();
        Date now = new Date();
        int count = notification.getCount() + 1;
        notification.setCount(count);
        notification.setUpdateTime(now);
        notification.setNotifyTime(now);
        notification.setNextTime(nextTime(count, now));
        notificationDao.saveAndFlush(notification);

        NotificationLog log = new NotificationLog();
        log.setId(UUID.randomUUID().toString());
        log.setCount(count);
        log.setCreateTime(now);
        log.setUpdateTime(now);
        log.setNotification(notification);

        return notificationLogDao.save(log);
    }

    private Date nextTime(int count, Date now) {
        int index = (Math.min(count, times.length)) - 1;
        return times[index].expire(now);
    }

    @Override
    public void updateNotificationLog(NotificationLog notificationLog) {
        Date now = new Date();
        notificationLog.setUpdateTime(now);
        if (StringUtils.length(notificationLog.getResult()) > 50) {
            notificationLog.setResult(notificationLog.getResult().substring(0, 50));
        }
        notificationLogDao.save(notificationLog);

        Notification notification = notificationDao.getOne(notificationLog.getNotification().getId());
        if (Const.SUCCESS.equals(notificationLog.getResult())) {
            notification.setUpdateTime(now);
            notification.setState("F");
            notification.setResult(Const.SUCCESS);
            notificationDao.saveAndFlush(notification);
        } else if (notificationLog.getCount() == notificationLog.getCount()) {
            notification.setResult(notificationLog.getResult());
            notification.setUpdateTime(now);
            notificationDao.saveAndFlush(notification);
        }
    }

    @Override
    public List<Notification> findAllNotificationOfRequired() {
        return notificationDao.findAllByStateNotAndNextTimeBefore("F", new Date());
    }
}
