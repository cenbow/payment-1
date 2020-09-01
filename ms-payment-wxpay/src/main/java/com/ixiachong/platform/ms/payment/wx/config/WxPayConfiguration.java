/**
 * Project: parent
 * Document: TransfersServiceImpl
 * Date: 2020/8/4 14:41
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.config;

import com.ixiachong.platform.commons.payment.service.MerchantService;
import com.ixiachong.platform.ms.payment.wx.domain.MerchantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class WxPayConfiguration {

  @Value(value = "${executor-config.core-size}")
  private int coreSize;

  @Value(value = "${executor-config.core-size}")
  private int maxSize;

  @Value(value = "${executor-config.keep-time}")
  private long keepTime;

  @Value(value = "${executor-config.queue-size}")
  private int queueSize;

  @Bean
  @ConditionalOnMissingBean
  public MerchantManager wxService(@Autowired MerchantService merchantService) {

    return MerchantManager.builder()
            .configFinder(no -> merchantService.getMerchantChannel("wxpay", no)).build();
  }

  @Bean(name = "executorService")
  public ExecutorService getExecutorService(){

    return  new ThreadPoolExecutor(coreSize, maxSize, keepTime,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize),new ThreadPoolExecutor.AbortPolicy());
  }

}
