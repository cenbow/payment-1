/*
 * Project: Accounts
 * Document: WithdrawCOnfigChannels
 * Date: 2020/8/20 11:00
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.merchant;

import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.platform.ms.payment.core.exceptions.MerchantException;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.request.MerchantConfigChannelRequest;
import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author fengzl
 * @Date 2020/8/20
 */
@Service
public class WithdrawConfigChannelsService extends AbstractService implements TradeHandler<MerchantConfigChannelRequest, Map<String, String>> {
    @Override
    public Map<String, String> handle(TradeRequest<MerchantConfigChannelRequest> request) throws TradeException {
        try {
            return MapUtils.map("merchantNo", service.configWithdraw(checkBizContent(request)));
        } catch (MerchantException ex) {
            throw new TradeException(ex.getMessage(), ex, ex.getCode());
        }
    }
}
