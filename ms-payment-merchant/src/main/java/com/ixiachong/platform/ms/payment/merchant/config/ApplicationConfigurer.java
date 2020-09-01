/*
 * Project: Payment
 * Document: ApplicationConfigurer
 * Date: 2020/8/12 4:45 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.config;

import com.ixiachong.platform.commons.payment.service.AccountService;
import com.ixiachong.platform.ms.payment.merchant.Application;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableDiscoveryClient
@EnableFeignClients(clients = {AccountService.class})
@EnableJpaRepositories(basePackageClasses = {Application.class})
public class ApplicationConfigurer {
}
