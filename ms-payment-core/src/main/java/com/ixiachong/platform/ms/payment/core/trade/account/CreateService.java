/*
 * Project: Payment
 * Document: CreateService
 * Date: 2020/8/19 7:13 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.account;

import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.platform.account.api.vo.AccountVo;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.service.impl.AbstractGenericService;
import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CreateService extends AbstractGenericService implements TradeHandler<CreateRequest, Map<String, String>> {
    @Override
    public Map<String, String> handle(TradeRequest<CreateRequest> request) throws TradeException {
        CreateRequest bizContent = checkBizContent(request);
        AccountVo account = accountService.createAccounts(bizContent.getCustomerNo());
        return MapUtils.map("accountNo", account.getNo(), "name", account.getName());
    }
}
