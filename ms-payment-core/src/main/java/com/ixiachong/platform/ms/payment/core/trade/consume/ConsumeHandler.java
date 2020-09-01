/*
 * Project: Payment
 * Document: ConsumeHandler
 * Date: 2020/8/8 11:15 上午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.consume;

import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import com.ixiachong.platform.ms.payment.core.trade.withdraw.Result;

public class ConsumeHandler implements TradeHandler<BizContent, Result> {
    @Override
    public Result handle(TradeRequest<BizContent> request) throws TradeException {
        return null;
    }
}
