/*
 * Project: Payment
 * Document: NotificationHttpClientConfigurer
 * Date: 2020/8/26 5:31 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.notification.config;

import com.ixiachong.platform.ms.payment.notification.Const;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationHttpClientConfigurer {
    @Bean(name = Const.NOTIFICATION_HTTP_CLIENT)
    @ConfigurationProperties(prefix = "application.notification.http")
    public PoolingHttpClientConnectionManager notificationHttpClientConnectionManager() {
        return new PoolingHttpClientConnectionManager();
    }
}
