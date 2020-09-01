/*
 * Project: Payment
 * Document: CreateService
 * Date: 2020/8/19 7:23 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.merchant;

import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.platform.commons.payment.dto.Merchant;
import com.ixiachong.platform.ms.payment.core.exceptions.MerchantException;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.request.MerchantCreateRequest;
import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CreateMerchantService extends AbstractService implements TradeHandler<MerchantCreateRequest, Map<String, String>> {


    @Override
    public Map<String, String> handle(TradeRequest<MerchantCreateRequest> request) throws TradeException {
        MerchantCreateRequest bizContent = checkBizContent(request);
        try {
            Merchant merchant = service.create(bizContent.getAccountNo(),
                    bizContent.getServices());
            return MapUtils.map("merchantNo", merchant.getNo(), "accountNo", merchant.getAccountNo());
        } catch (MerchantException ex) {
            throw new TradeException(ex.getMessage(), ex, ex.getCode());
        }
    }
}
