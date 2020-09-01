/*
 * Project: Payment
 * Document: TradeProviderConfigurer
 * Date: 2020/8/19 5:36 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.config;

import com.ixiachong.commons.etc.container.BeanFinder;
import com.ixiachong.platform.ms.payment.core.common.ObjectMapper;
import com.ixiachong.platform.ms.payment.core.trade.Configurer;
import com.ixiachong.platform.ms.payment.core.trade.TradeRegister;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Configuration
public class TradeProviderConfigurer implements Configurer {

    private TradeProperties properties;
    private ObjectMapper objectMapper;
    private BeanFinder beanFinder;

    @Autowired
    public void setBeanFinder(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setProperties(TradeProperties properties) {
        this.properties = properties;
    }

    @Override
    public void configure(TradeRegister register) {
        if (properties == null || ArrayUtils.isEmpty(properties.getProviders())) {
            return;
        }

        Stream.of(properties.getProviders()).forEach(provider -> {
            String code = provider.getCode();
            if (provider.getBizClass() != null) {
                register.setBizResolvers(code, content -> objectMapper.readObject(content, provider.getBizClass()));
            }
            if (provider.getHandlerClass() != null) {
                register.setTradeHandler(code, beanFinder.findOne(provider.getHandlerClass()));
            }
        });
    }
}
