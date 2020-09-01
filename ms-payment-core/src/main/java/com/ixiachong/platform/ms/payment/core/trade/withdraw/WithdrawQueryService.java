/*
 * Project: Accounts
 * Document: WithdrawQueryService
 * Date: 2020/8/24 18:24
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.withdraw;

import com.ixiachong.commons.etc.exceptions.BaseException;
import com.ixiachong.platform.ms.payment.core.exceptions.Errors;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.model.trans.Withdraw;
import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author fengzl
 * @Date 2020/8/24
 */
@Service
public class WithdrawQueryService extends AbstractService implements TradeHandler<WithdrawQueryRequest, Result> {
    @Override
    public Result handle(TradeRequest<WithdrawQueryRequest> request) throws BaseException {
        WithdrawQueryRequest bizContent = checkBizContent(request);

        if (StringUtils.isNoneEmpty(bizContent.getTradeNo(), bizContent.getOutTradeNo())
                || StringUtils.isAllEmpty(bizContent.getOutTradeNo(), bizContent.getTradeNo())) {
            Errors.PARAMETERS_ERROR.throwException(TradeException.class);
        }

        Optional<Withdraw> optional;
        if (StringUtils.isNotEmpty(bizContent.getOutTradeNo())) {
            optional = withdrawDao.findByOutTradeNoAndAppId(bizContent.getOutTradeNo(), request.getAppId());
        } else {
            optional = withdrawDao.findByTradeNo(bizContent.getTradeNo());
        }

        return optional.map(this::toResult).orElse(null);
    }
}
