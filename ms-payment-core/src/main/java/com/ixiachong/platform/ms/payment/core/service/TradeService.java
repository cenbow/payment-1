/*
 * Project: Payment
 * Document: TradeService
 * Date: 2020/8/8 10:22 上午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.service;

import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import reactor.core.publisher.Mono;

public interface TradeService {
    Mono<?> handle(TradeRequest<String> request) throws TradeException;
}
