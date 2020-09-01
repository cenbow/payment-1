/*
 * Project: Payment
 * Document: TradeMessageFilter
 * Date: 2020/7/20 3:39 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic.filter;

import com.ixiachong.commons.etc.exceptions.BaseException;
import com.ixiachong.commons.etc.exceptions.Message;
import com.ixiachong.platform.ms.payment.gateway.generic.Const;
import com.ixiachong.platform.ms.payment.gateway.generic.config.TradeMessageProperties;
import com.ixiachong.platform.ms.payment.gateway.generic.exceptions.TradeParameterException;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Log
@Getter
public class TradeMessageFilter extends AbstractTradeFilter implements GlobalFilter, Ordered {

    private static final Collection<String> REQUIRE_PARAMS = Arrays.asList(
            Const.APP_ID, Const.BIZ_CONTENT, Const.SIGN, Const.TIMESTAMP);
    private static final String BODY_BUFFER = "body-buffer";

    private final TradeMessageProperties properties;

    public TradeMessageFilter(TradeMessageProperties properties) {
        this.properties = properties;
    }

    private void process(MonoSink<ServerWebExchange> sink, MultiValueMap<String, String> params,
                         Supplier<ServerWebExchange> exchangeSupplier) {
        try {
            this.checkParameters(params); // 检查必要参数
            if (properties.isVerifySign()) {
                this.checkSign(params); // 验证签名
            }
        } catch (BaseException ex) {
            sink.error(ex);
        }
        sink.success(exchangeSupplier.get());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return Mono.<ServerWebExchange>deferWithContext(context -> Mono.create(sink -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            if (headers.getContentLength() > 0) {
                DataBufferUtils.join(request.getBody())
                        .subscribe(dataBuffer -> {
                            ByteBuffer byteBuffer = dataBuffer.asByteBuffer();
                            DataBuffer requestBody = dataBuffer.factory().wrap(byteBuffer.duplicate());

                            Charset charset = StandardCharsets.UTF_8;
                            CharBuffer charBuffer = charset.decode(byteBuffer);
                            DataBufferUtils.release(dataBuffer);

                            String body = charBuffer.toString();
                            MultiValueMap<String, String> params = parseFormData(charset, body);
                            if (!CollectionUtils.isEmpty(request.getQueryParams())) {
                                request.getQueryParams().forEach(params::addAll);
                            }

                            exchange.getAttributes().put(Const.REQUEST_TRADE_ATTRIBUTE, params);
                            process(sink, params, () -> exchange.mutate()
                                    .request(new ServerHttpRequestDecorator(request) {
                                        @Override
                                        @Nonnull
                                        public Flux<DataBuffer> getBody() {
                                            return Flux.just(requestBody);
                                        }
                                    }).build());
                        });
            } else {
                exchange.getAttributes().put(Const.REQUEST_TRADE_ATTRIBUTE, request.getQueryParams());
                process(sink, request.getQueryParams(), () -> exchange);
            }
        })).flatMap(chain::filter)
                .onErrorResume(throwable -> {
                    Message message = throwable instanceof Message
                            ? (Message) throwable
                            : new TradeParameterException("未知异常:" + throwable.getMessage(), "error");
                    responseException(message, exchange);
                    return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().allocateBuffer()));
                });
    }

    @Override
    public int getOrder() {
        return this.properties.getOrder();
    }

    private void checkParameters(Map<String, ?> params) throws TradeParameterException {
        Optional<String> needParam = REQUIRE_PARAMS.stream().filter(key -> !params.containsKey(key))
                .findFirst();
        if (needParam.isPresent()) {
            throw new TradeParameterException("缺少必要参数:" + needParam.get(), "MISSING_PARAMETER");
        }
    }

    private void checkSign(Map<String, List<String>> params) throws BaseException {
        Map<String, String> data = params.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> String.join("", entry.getValue())));

        String appId = data.get(Const.APP_ID);
        String sign = data.get(Const.SIGN);
        String signType = data.getOrDefault(Const.SIGN_TYPE, Const.MD5);

        if (!cryptoService.verifySign(Const.SCENE_PAYMENT_GATEWAY, appId, signType, sign, data)) {
            throw new BaseException("无效的签名", "INVALID_SIGNATURE");
        }
    }

    private void responseException(Message message, ServerWebExchange exchange) {
        exchange.getAttributes().put(Message.class.getName(), message);
    }

    private MultiValueMap<String, String> parseFormData(Charset charset, String body) {
        String[] pairs = StringUtils.tokenizeToStringArray(body, "&");
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>(pairs.length);
        try {
            for (String pair : pairs) {
                int idx = pair.indexOf('=');
                if (idx == -1) {
                    result.add(URLDecoder.decode(pair, charset.name()), null);
                } else {
                    String name = URLDecoder.decode(pair.substring(0, idx), charset.name());
                    String value = URLDecoder.decode(pair.substring(idx + 1), charset.name());
                    result.add(name, value);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
        return result;
    }
}
