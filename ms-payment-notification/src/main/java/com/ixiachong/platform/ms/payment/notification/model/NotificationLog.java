/*
 * Project: Payment
 * Document: NotificationLog
 * Date: 2020/8/26 5:20 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.notification.model;

import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification_log")
@Data
@EqualsAndHashCode(of = {"id"})
public class NotificationLog extends AbstractCreatedEntity {
    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;
    @Column(name = "count_")
    private int count;
    @Column(name = "request")
    private String request;
    @Column(name = "response")
    private String response;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_time")
    private Date requestTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "response_time")
    private Date responseTime;
    @Column(name = "result", length = 100)
    private String result;
}
