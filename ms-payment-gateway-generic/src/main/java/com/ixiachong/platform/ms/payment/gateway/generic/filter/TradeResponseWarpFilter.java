/*
 * Project: Payment
 * Document: TradeResponseWarpFilter
 * Date: 2020/7/22 7:36 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ixiachong.commons.etc.Result;
import com.ixiachong.commons.etc.exceptions.Message;
import com.ixiachong.platform.ms.payment.gateway.generic.Const;
import com.ixiachong.platform.ms.payment.gateway.generic.beans.TradeResponse;
import com.ixiachong.platform.ms.payment.gateway.generic.config.TradeResponseProperties;
import com.ixiachong.platform.ms.payment.gateway.generic.utils.DateUtils;
import com.ixiachong.platform.ms.payment.gateway.generic.utils.HttpUtils;
import lombok.extern.java.Log;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Log
public class TradeResponseWarpFilter extends AbstractTradeFilter implements GlobalFilter, Ordered {
    private static final String ERROR_CODE = "BASE_EXCEPTION_CODE";
    private static final String ERROR_MESSAGE = "BASE_EXCEPTION_MESSAGE";
    private static final String ERROR_BODY = "BASE_EXCEPTION_BODY";
    private final TradeResponseProperties properties;

    public TradeResponseWarpFilter(TradeResponseProperties properties) {
        this.properties = properties;
    }

    private ObjectMapper objectMapper;

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(@Nonnull Publisher<? extends DataBuffer> body) {
                DataBufferFactory factory = this.bufferFactory();

                TradeResponse tr = TradeResponse.builder().build();
                Message exMessage = exchange.getAttribute(Message.class.getName());
                AtomicBoolean includeBody = new AtomicBoolean(true);
                HttpHeaders headers = this.getHeaders();

                if (exMessage != null) {
                    tr.setCode(exMessage.getCode());
                    tr.setMessage(exMessage.getMessage());
                } else if (headers.containsKey(ERROR_CODE)) {
                    tr.setCode(String.join("", headers.get(ERROR_CODE)));
                    tr.setMessage(decode(String.join("", headers.get(ERROR_MESSAGE))));
                    includeBody.set(HttpUtils.getHeaderBoolean(headers, ERROR_BODY));

                    headers.remove(ERROR_CODE);
                    headers.remove(ERROR_MESSAGE);
                    headers.remove(ERROR_BODY);
                } else {
                    if (HttpStatus.OK.equals(this.getStatusCode())) {
                        tr.setCode(Result.SUCCESS_CODE);
                        tr.setMessage(Result.SUCCESS_CODE);
                    } else {
                        tr.setCode(Result.FAILURE_CODE);
                        tr.setMessage(Result.FAILURE_CODE);
                    }
                }

                MultiValueMap<String, String> params = (MultiValueMap<String, String>) exchange.getAttributes()
                        .get(Const.REQUEST_TRADE_ATTRIBUTE);
                tr.setAppId(params.getFirst(Const.APP_ID));
                tr.setAttach(params.getFirst(Const.ATTACH));
                tr.setSignType(params.getFirst(Const.SIGN_TYPE));
                tr.setTimestamp(DateUtils.currentTimestamp());
                tr.setFormat(Const.JSON);
                tr.setCharset(StandardCharsets.UTF_8.name());

                headers.remove(HttpHeaders.CONTENT_LENGTH);
                headers.set(HttpHeaders.TRANSFER_ENCODING, Const.TRANSFER_ENCODING_CHUNKED);
                headers.setContentType(MediaType.APPLICATION_JSON);

                Flux<DataBuffer> data;
                try {
                    data = Flux.from(body)
                            .collect(factory::allocateBuffer, DataBuffer::write)
                            .map(buffer -> {
                                if (includeBody.get() && buffer.readableByteCount() > 0) {
                                    tr.setBizContent(buffer.toString(StandardCharsets.UTF_8));
                                }

                                if (properties.isSign() && StringUtils.isNotBlank(tr.getAppId())) {
                                    Map<String, String> signData = Arrays.stream(PropertyUtils.getPropertyDescriptors(tr))
                                            .map(pd -> new String[]{pd.getName(), value(tr, pd.getName())})
                                            .filter(ary -> null != ary[1])
                                            .collect(Collectors.toMap(ary -> ary[0], ary -> ary[1]));

                                    tr.setSignType(StringUtils.defaultIfBlank(tr.getSignType(), Const.MD5));
                                    tr.setSign(cryptoService.sign(Const.SCENE_PAYMENT_GATEWAY,
                                            tr.getAppId(),
                                            tr.getSignType(),
                                            signData));
                                }
                                try {
                                    return factory.wrap(objectMapper.writeValueAsBytes(tr));
                                } catch (Exception ex) {
                                    log.log(Level.WARNING, ex.getMessage(), ex);
                                    return factory.wrap(ex.getMessage().getBytes());
                                }
                            }).flux();
                } catch (Exception ex) {
                    data = Flux.just(factory.wrap(ex.getMessage().getBytes()));
                }

                return super.writeWith(data);
            }
        };

        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return properties.getOrder();
    }

    private String value(Object bean, String name) {
        try {
            return ConvertUtils.convert(PropertyUtils.getSimpleProperty(bean, name));
        } catch (Exception ex) {
            return null;
        }
    }

    private String decode(String value) {
        return org.springframework.util.StringUtils.isEmpty(value) ? value : new String(Base64.getDecoder().decode(value));
    }
}
