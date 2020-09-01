/*
 * Project: Accounts
 * Document: DeleteWithdrawConfigService
 * Date: 2020/8/20 11:27
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.merchant;

import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.request.BaseMerchantChannelRequest;
import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author fengzl
 * @Date 2020/8/20
 */
@Service
public class DeleteWithdrawConfigService extends AbstractService implements TradeHandler<BaseMerchantChannelRequest, Map<String, String>> {
    @Override
    public Map<String, String> handle(TradeRequest<BaseMerchantChannelRequest> request) throws TradeException {
        BaseMerchantChannelRequest bizContent = checkBizContent(request);
        return MapUtils.map("merchantNo", service.delConfigWithdraw(bizContent.getMerchantNo(), bizContent.getChannel()));
    }
}
