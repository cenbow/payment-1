/*
 * Project: Payment
 * Document: TradeCoreConfigurer
 * Date: 2020/8/19 3:35 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.config;

import com.ixiachong.platform.ms.payment.core.trade.Configurer;
import com.ixiachong.platform.ms.payment.core.trade.TradeRegister;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TradeCoreConfigurer {
    private List<Configurer> configurers;

    @Autowired(required = false)
    public void setConfigurers(List<Configurer> configurers) {
        this.configurers = configurers;
    }

    @Bean
    public TradeRegister tradeRegister() {
        TradeRegister register = new TradeRegister();

        if (CollectionUtils.isNotEmpty(configurers)) {
            configurers.forEach(configurer -> configurer.configure(register));
        }

        return register;
    }
}
