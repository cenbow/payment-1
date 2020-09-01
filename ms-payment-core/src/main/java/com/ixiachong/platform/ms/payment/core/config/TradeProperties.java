/*
 * Project: Payment
 * Document: TradeProperties
 * Date: 2020/8/19 5:54 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.config;

import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.trade")
public class TradeProperties {
    private Provider[] providers;

    @Data
    public static class Provider {
        private String code;
        private Class<TradeHandler> handlerClass;
        private Class<?> bizClass;
    }
}
