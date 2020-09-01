/*
 * Project: Payment
 * Document: TradeRegister
 * Date: 2020/8/19 3:38 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TradeRegister {
    private Map<String, TradeHandler> handlers = new HashMap<>();
    private Map<String, Function<String, ?>> bizResolvers = new HashMap<>();

    public void setTradeHandler(String code, TradeHandler handler) {
        handlers.put(code, handler);
    }

    public void setBizResolvers(String code, Function<String, ?> resolver) {
        bizResolvers.put(code, resolver);
    }

    public TradeHandler getTradeHandler(String code) {
        return handlers.get(code);
    }

    public Function<String, ?> getBizResolver(String code) {
        return bizResolvers.get(code);
    }
}
