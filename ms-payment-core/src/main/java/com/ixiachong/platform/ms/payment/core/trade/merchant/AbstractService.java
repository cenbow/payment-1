/*
 * Project: Accounts
 * Document: AbstractService
 * Date: 2020/8/20 11:04
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.merchant;

import com.ixiachong.platform.ms.payment.core.exceptions.Errors;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.service.MerchantConfigService;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author fengzl
 * @Date 2020/8/20
 */
public class AbstractService {
    protected MerchantConfigService service;

    @Autowired
    public void setService(MerchantConfigService service) {
        this.service = service;
    }

    protected <T> T checkBizContent(TradeRequest<T> request) throws TradeException {
        if (request.getBizContent() == null){
            Errors.PARAMETERS_ERROR.throwException(TradeException.class);
        }
        return request.getBizContent();
    }
}
