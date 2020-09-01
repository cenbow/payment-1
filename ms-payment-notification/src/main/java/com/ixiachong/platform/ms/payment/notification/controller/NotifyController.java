/*
 * Project: Payment
 * Document: NotifyController
 * Date: 2020/8/27 3:55 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.notification.controller;

import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.commons.etc.exceptions.BaseException;
import com.ixiachong.platform.ms.payment.notification.model.Notification;
import com.ixiachong.platform.ms.payment.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifies")
public class NotifyController {
    private NotificationService service;

    @Autowired
    public void setService(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public Object create(@RequestBody com.ixiachong.platform.commons.payment.notify.Notification notify) throws BaseException {
        Notification notification = this.service.startNotifyResult(notify.getType(), notify.getIdentify(), notify.getParams());
        if (notification != null) {
            return MapUtils.map("id", notification.getId());
        } else {
            throw new BaseException("未知异常", "ERROR");
        }
    }
}
