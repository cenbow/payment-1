/*
 * Project: Payment
 * Document: GatewayBaseTest
 * Date: 2020/8/24 5:37 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.cloud.zookeeper.discovery.root=/develop/wangbz/services",
                "spring.cloud.zookeeper.connect-string=zk.dev.sz.xiac.io:2181",
                "spring.cloud.zookeeper.discovery.prefer-ip-address=true",
                "spring.cloud.config.discovery.enabled=false",
                "spring.cloud.config.uri=http://config.wangbz.ins.sz.xiac.io",
                "spring.cloud.config.name=${spring.application.name}"
        })
public abstract class GatewayBaseTest {
    @Autowired(required = false)
    protected WebTestClient client;

    @Data
    public static class Result<T> {
        private String appId;
        private String code;
        private String message;
        private T bizContent;
    }
}
