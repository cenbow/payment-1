/*
 * Project: Payment
 * Document: HandlerService
 * Date: 2020/8/19 6:33 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.customer;

import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.platform.account.api.dto.EnterpriseDto;
import com.ixiachong.platform.account.api.dto.PersonalDto;
import com.ixiachong.platform.account.api.request.CustomerRegisterRequest;
import com.ixiachong.platform.account.api.vo.CustomerRegisterVo;
import com.ixiachong.platform.ms.payment.core.Const;
import com.ixiachong.platform.ms.payment.core.exceptions.Errors;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.service.impl.AbstractGenericService;
import com.ixiachong.platform.ms.payment.core.trade.TradeHandler;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log
@Service
public class HandlerService extends AbstractGenericService implements TradeHandler<Request, Map<String, String>> {
    @Override
    public Map<String, String> handle(TradeRequest<Request> request) throws TradeException {
        Request biz = checkBizContent(request);
        CustomerRegisterRequest customerRequest = new CustomerRegisterRequest();
        org.springframework.beans.BeanUtils.copyProperties(biz, customerRequest);
        customerRequest.setType(Const.CUSTOMER_TYPES.get(biz.getType()));
        switch (customerRequest.getType()) {
            case ENTERPRISE:
                customerRequest.setEnterprise(BeanUtils.mapping(biz, EnterpriseDto.class));
                break;
            case PERSONAL:
                PersonalDto personalDto = new PersonalDto();
                org.springframework.beans.BeanUtils.copyProperties(biz, personalDto);
                personalDto.setGender(Const.GENDERS.get(biz.getGender()));
                customerRequest.setPersonal(personalDto);
                break;
            default:
                Errors.CUSTOMER_TYPE_INVALID.throwException(TradeException.class);
        }
        CustomerRegisterVo registerVo = accountService.registerCustomer(customerRequest);

        return MapUtils.map("customerNo", registerVo.getNo());
    }
}
