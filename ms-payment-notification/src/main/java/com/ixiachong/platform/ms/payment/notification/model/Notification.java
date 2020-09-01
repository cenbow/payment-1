/*
 * Project: Payment
 * Document: Notification
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
@Table(name = "notification")
@Data
@EqualsAndHashCode(of = {"id"})
public class Notification extends AbstractCreatedEntity {
    @Column(name = "type", length = 30)
    private String type;
    @Column(name = "identify", length = 36, columnDefinition = "CHAR(36)")
    private String identify;
    @Column(name = "count_")
    private int count;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "notify_time")
    private Date notifyTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "next_time")
    private Date nextTime;
    @Column(name = "result", length = 100)
    private String result;
    @Column(name = "state_", length = 1, columnDefinition = "CHAR(1)")
    private String state;
    @Lob
    @Column(name = "params")
    private String params;
}
