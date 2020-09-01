/*
 * Project: Accounts
 * Document: GetWithdrawConfigService
 * Date: 2020/8/20 11:45
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.merchant;

import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.request.BaseMerchantRequest;
import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author fengzl
 * @Date 2020/8/20
 */
@Service
public class GetWithdrawConfigService extends AbstractService implements TradeHandler<BaseMerchantRequest, Map<String, Object>> {
    @Override
    public Map<String, Object> handle(TradeRequest<BaseMerchantRequest> request) throws TradeException {
        String merchantNo = checkBizContent(request).getMerchantNo();
        return MapUtils.map("merchantNo", merchantNo, "channels", service.getConfigWithdraw(merchantNo));
    }
}
