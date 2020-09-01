/*
 * Project: Payment
 * Document: TradeServiceImpl
 * Date: 2020/8/8 10:44 上午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.service.impl;

import com.ixiachong.platform.ms.payment.core.Const;
import com.ixiachong.platform.ms.payment.core.exceptions.Errors;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.service.TradeService;
import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import com.ixiachong.platform.ms.payment.core.trade.TradeRegister;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.util.function.Function;

@Service
public class TradeServiceImpl extends AbstractGenericService implements TradeService {
    private TradeRegister register;

    @Autowired
    public void setRegister(TradeRegister register) {
        this.register = register;
    }

    @Override
    public Mono<?> handle(TradeRequest<String> request) throws TradeException {
        String code = request.getMethod();
        TradeHandler handler = register.getTradeHandler(code);
        if (handler == null) {
            Errors.TRADE_HANDLER_NOT_FOUND.throwException(TradeException.class);
        }

        return Mono.create(sink -> {
            Function<String, ?> resolver = register.getBizResolver(code);
            if (resolver != null) {
                TradeRequest<Object> handlerRequest = new TradeRequest<>();
                BeanUtils.copyProperties(request, handlerRequest, Const.BIZ_CONTENT);
                handlerRequest.setBizContent(resolver.apply(request.getBizContent()));
                this.submitTrade(sink, handler, handlerRequest);
            } else {
                this.submitTrade(sink, handler, request);
            }
        });
    }

    @Async
    protected void submitTrade(MonoSink sink, TradeHandler handler, TradeRequest request) {
        try {
            Object result = handler.handle(request);
            if (result instanceof Mono) {
                ((Mono<?>) result).subscribe(sink::success, sink::error);
            } else {
                sink.success(result);
            }
        } catch (Exception ex) {
            sink.error(ex);
        }
    }
}
