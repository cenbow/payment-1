/*
 * Project: Accounts
 * Document: ChannelConfigre
 * Date: 2020/8/22 14:33
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.model.trans;

import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * @Author fengzl
 * @Date 2020/8/22
 */
@Setter
@Getter
//@Entity
//@Table(name = "channel_configurer`")
//TODO:渠道错误码映射类  待完善
public class ChannelConfigurer extends AbstractCreatedEntity {
    @Column(name = "channel", length = 10)
    private String channel;

    @Column(name = "code", length = 10)
    private String code;

    @Column(name = "status", length = 10)
    private String status;
}
