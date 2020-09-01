/**
 * Project: parent
 * Document: AlipayConfig
 * Date: 2020/8/12 14:20
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.config;

import com.ixiachong.platform.commons.payment.service.MerchantService;
import com.ixiachong.platform.ms.payment.alipay.domain.MerchantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @Author: wangcy
 */
@Configuration
public class AlipayConfigurer {

    @Value(value = "${executor-config.core-size}")
    private int coreSize;

    @Value(value = "${executor-config.core-size}")
    private int maxSize;

    @Value(value = "${executor-config.keep-time}")
    private long keepTime;

    @Value(value = "${executor-config.queue-size}")
    private int queueSize;

    @Value(value = "${temp.directory}")
    private String tempDirectory;


    @Bean
    public MerchantManager merchantManager(@Autowired MerchantService merchantService) {

        return MerchantManager.builder()
                .configFinder(no -> merchantService.getMerchantChannel("alipay", no))
                .directory(new File(tempDirectory))
                .build();
    }

    @Bean(name = "executorService")
    public ExecutorService getExecutorService() {
        return  new ThreadPoolExecutor(coreSize, maxSize, keepTime,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize),new ThreadPoolExecutor.AbortPolicy());
    }
}
