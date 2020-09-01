/*
 * Project: Accounts
 * Document: ApplicationConfigurer
 * Date: 2020/8/6 4:00 下午
 * Author: wangcy
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.config;

import com.ixiachong.platform.commons.payment.service.MerchantService;
import com.ixiachong.platform.ms.payment.alipay.Application;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 应用程序通用配置类
 *
 * @author wangcy
 */
@Configuration
@EnableDiscoveryClient
@EnableFeignClients(clients = {MerchantService.class})
@EnableJpaRepositories(basePackageClasses = {Application.class})
public class ApplicationConfigurer {
}
