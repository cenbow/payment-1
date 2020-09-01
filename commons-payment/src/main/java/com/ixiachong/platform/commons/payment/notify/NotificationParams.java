/*
 * Project: Payment
 * Document: NotificationParams
 * Date: 2020/8/26 7:27 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.notify;


import lombok.Data;

import java.io.Serializable;

@Data
public class NotificationParams implements Serializable {
    private String appId;
    private String code;
    private String message;
    private String notifyUrl;
    private String attach;
    private String bizContent;
}
