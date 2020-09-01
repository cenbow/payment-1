/*
 * Project: Payment
 * Document: JSONConfigurer
 * Date: 2020/8/19 4:52 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Level;

@Log
@Configuration
public class JSONConfigurer {
    private ObjectMapper mapper;

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public com.ixiachong.platform.ms.payment.core.common.ObjectMapper objectReader() {
        return new com.ixiachong.platform.ms.payment.core.common.ObjectMapper() {
            @Override
            public <T> T readObject(String content, Class<T> clazz) {
                try {
                    return mapper.readValue(content, clazz);
                } catch (JsonProcessingException e) {
                    log.log(Level.WARNING, e.getMessage(), e);
                    return null;
                }
            }
        };
    }
}
