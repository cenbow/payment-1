/*
 * Project: Payment
 * Document: FeignTest
 * Date: 2020/7/24 4:36 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic;

import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.platform.commons.payment.service.CryptoService;
import com.ixiachong.platform.ms.payment.gateway.generic.config.WebConfigurer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        properties = {
                "spring.cloud.zookeeper.discovery.root=/develop/wangbz/services",
                "spring.cloud.zookeeper.discovery.prefer-ip-address=true",
                "spring.cloud.zookeeper.connect-string=zk.dev.sz.xiac.io:2181"
        },
        classes = {FeignTest.class, WebConfigurer.class})
@EnableFeignClients(clients = CryptoService.class)
@EnableAutoConfiguration
public class FeignTest {

    @Autowired
    CryptoService service;

    @Test
    public void test() {
        Assert.assertNotNull(service);

        Assert.assertTrue(service.verifySign("test", "test", "test", "test",
                MapUtils.map("app_id", "123456")));
    }
}
