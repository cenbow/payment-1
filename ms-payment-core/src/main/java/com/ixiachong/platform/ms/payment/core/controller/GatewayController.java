/*
 * Project: Payment
 * Document: GatewayController
 * Date: 2020/8/19 2:47 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.controller;

import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.service.TradeService;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {
    private TradeService service;

    @Autowired
    public void setService(TradeService service) {
        this.service = service;
    }

    @RequestMapping("/gateway")
    public Mono<?> gateway(TradeRequest<String> request) throws TradeException {
        return service.handle(request);
    }
}
