/*
 * Project: Payment
 * Document: ApplicationConfigurer
 * Date: 2020/5/26 9:25 下午
 * Author: wangbz
 * <p>
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.config;

import com.ixiachong.platform.commons.payment.service.*;
import com.ixiachong.platform.ms.payment.core.Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 应用程序通用配置类
 *
 * @author wangbz
 */

@EnableAsync
@Configuration
@EnableDiscoveryClient
@EnableFeignClients(clients = {AccountService.class, TransactionService.class,
        WxPayService.class, MerchantService.class, AliPayService.class})
@EnableJpaRepositories(basePackageClasses = {Application.class})
public class ApplicationConfigurer {
    @Value("${executor-config.core-size:5}")
    private Integer corePoolSize;

    @Value("${executor-config.max-size:20}")
    private Integer maximumPoolSize;

    @Value("${executor-config.keep-time:10}")
    private Integer keepAliveTime;

    @Value("${executor-config.queue-size:200}")
    private Integer capacity;

    @Bean(name = "executorService")
    public ExecutorService getExecutorService() {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(capacity), new ThreadPoolExecutor.AbortPolicy());
    }
}
