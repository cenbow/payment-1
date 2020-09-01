/*
 * Project: Payment
 * Document: ApplicationConfigurer
 * Date: 2020/7/20 3:01 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic.config;

import com.ixiachong.platform.commons.payment.service.CryptoService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * 应用程序通用配置类
 */
@Configuration
@EnableCaching
@EnableDiscoveryClient
@EnableFeignClients(clients = {CryptoService.class})
@EnableAutoConfiguration
public class ApplicationConfigurer {
}
