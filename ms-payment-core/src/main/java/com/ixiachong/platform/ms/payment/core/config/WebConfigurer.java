/*
 * Project: Accounts
 * Document: WebConfigurer
 * Date: 2020/7/28 14:58
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.commons.etc.exceptions.BaseRuntimeException;
import com.ixiachong.platform.ms.payment.core.Const;
import com.ixiachong.platform.ms.payment.core.exceptions.Errors;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.logging.Level;

/**
 * @Author fengzl
 * @Date 2020/7/28
 */
@Log
@Configuration
@EnableWebMvc
public class WebConfigurer implements WebMvcConfigurer {
    private static Collection<String> REQUIRE_PARAMS = Collections.unmodifiableCollection(
            Arrays.asList(Const.REQUEST_ID, Const.APP_ID, Const.TIMESTAMP, Const.SIGN, Const.BIZ_CONTENT));

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter methodParameter) {
                return TradeRequest.class.isAssignableFrom(methodParameter.getParameterType());
            }

            @Override
            public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer model,
                                          NativeWebRequest webRequest, WebDataBinderFactory factory) throws Exception {
                Set<String> keys = webRequest.getParameterMap().keySet();
                Optional<String> needParam = REQUIRE_PARAMS.stream().filter(key -> !keys.contains(key))
                        .findFirst();
                if (needParam.isPresent()) {
                    throw new BaseRuntimeException("缺少必要参数:" + needParam.get(), "MISSING_PARAMETER");
                }

                ParameterizedType type = (ParameterizedType) methodParameter.getParameter().getParameterizedType();
                Class<?> bizContentClass = (Class) type.getActualTypeArguments()[0];
                TradeRequest request;
                if (String.class.equals(bizContentClass)) {
                    request = BeanUtils.mapping(webRequest::getParameter, TradeRequest.class);
                } else {
                    request = BeanUtils.mapping(name -> Const.BIZ_CONTENT.equals(name)
                            ? parseJson(webRequest.getParameter(name), bizContentClass)
                            : webRequest.getParameter(name), TradeRequest.class);
                }

                if (null == request.getBizContent()) {
                    Errors.PARAMETERS_ERROR.throwException(TradeException.class);
                }
                return request;
            }
        });
    }

    private Object parseJson(String json, Class<?> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }
}