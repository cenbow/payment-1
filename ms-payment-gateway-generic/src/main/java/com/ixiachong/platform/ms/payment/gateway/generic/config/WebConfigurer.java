/*
 * Project: Payment
 * Document: WebConfigurer
 * Date: 2020/7/20 3:21 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ixiachong.platform.commons.payment.service.CryptoService;
import com.ixiachong.platform.ms.payment.gateway.generic.Const;
import com.ixiachong.platform.ms.payment.gateway.generic.filter.TradeMessageFilter;
import com.ixiachong.platform.ms.payment.gateway.generic.filter.TradeResponseWarpFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

@Configuration
public class WebConfigurer {
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    @Bean
    @ConfigurationProperties(prefix = Const.TRADE_MESSAGE_FILTER_CONFIG_PREFIX)
    public TradeMessageProperties tradeMessageProperties() {
        return new TradeMessageProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = Const.TRADE_RESPONSE_FILTER_CONFIG_PREFIX)
    public TradeResponseProperties tradeResponseProperties() {
        return new TradeResponseProperties();
    }


    @Bean
    public TradeMessageFilter tradeMessageFilter(@Autowired TradeMessageProperties properties, @Autowired CryptoService cryptoService) {
        TradeMessageFilter filter = new TradeMessageFilter(properties);
        filter.setCryptoService(cryptoService);
        return filter;
    }

    @Bean
    public TradeResponseWarpFilter tradeResponseWarpFilter(
            @Autowired TradeResponseProperties properties,
            @Autowired CryptoService cryptoService,
            @Autowired ObjectMapper objectMapper) {
        TradeResponseWarpFilter filter = new TradeResponseWarpFilter(properties);
        filter.setCryptoService(cryptoService);
        filter.setObjectMapper(objectMapper);
        return filter;
    }
}
