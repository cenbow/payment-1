/*
 * Project: Payment
 * Document: NotificationDao
 * Date: 2020/8/26 5:46 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.notification.dao;

import com.ixiachong.platform.ms.payment.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface NotificationDao extends JpaRepository<Notification, String> {
    Notification getFirstByTypeAndIdentifyAndStateNotIn(String type, String identify, Collection<String> states);

    List<Notification> findAllByStateNotAndNextTimeBefore(String state, Date now);
}
