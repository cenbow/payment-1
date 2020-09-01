/*
 * Project: Accounts
 * Document: MerchantServiceImpl
 * Date: 2020/8/14 17:58
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.service.impl;

import com.ixiachong.platform.account.api.vo.AccountVo;
import com.ixiachong.platform.commons.payment.dto.Merchant;
import com.ixiachong.platform.commons.payment.service.AccountService;
import com.ixiachong.platform.commons.payment.service.MerchantService;
import com.ixiachong.platform.ms.payment.core.Const;
import com.ixiachong.platform.ms.payment.core.exceptions.Errors;
import com.ixiachong.platform.ms.payment.core.exceptions.MerchantException;
import com.ixiachong.platform.ms.payment.core.request.MerchantConfigChannelRequest;
import com.ixiachong.platform.ms.payment.core.request.MerchantConfigRequest;
import com.ixiachong.platform.ms.payment.core.service.MerchantConfigService;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Author fengzl
 * @Date 2020/8/14
 */
@Service
@Validated
public class MerchantConfigServiceImpl extends AbstractGenericService implements MerchantConfigService {
    private MerchantService merchantService;
    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @Override
    public Merchant create(@NotEmpty String accountNo, @NotEmpty String[] services) throws MerchantException {
        AccountVo accountVo = accountService.getAccountSummary(accountNo);
        if (null == accountVo || StringUtils.isEmpty(accountVo.getCustomerNo())) {
            Errors.CUSTOMER_NOT_EXISTS.throwException(MerchantException.class);
        }

        if (null == accountVo.getState() || !accountVo.getState().equals(Const.AVAILABILITY)) {
            Errors.ACCOUNT_UNUSABLE.throwException(MerchantException.class);
        }

        return merchantService.create(accountVo.getCustomerNo(), accountNo, services);
    }

    @Override
    public String configWithdraw(@NotEmpty String merchantNo, @NotEmpty Map<String, Map<String, String>> map) throws MerchantException {
        if (map.isEmpty()) {
            Errors.MERCHANT_NOT_CONFIG.throwException(MerchantException.class);
        }
        map.forEach((k, v) -> {
            v.put(Const.CHANNEL, k);
            merchantService.createChannel(merchantNo, v);
        });
        return merchantNo;
    }

    @Override
    public String configWithdraw(@NotNull MerchantConfigRequest request) throws MerchantException {
        return configWithdraw(request.getMerchantNo(), request.getChannels());
    }

    @Override
    public String configWithdraw(@NotNull MerchantConfigChannelRequest request) throws MerchantException {
        String channel = request.getChannel();
        Map<String, Map<String, String>> map = Maps.newHashMap();
        map.put(channel, request.getProperties());
        return configWithdraw(request.getMerchantNo(), map);
    }

    @Override
    public String delConfigWithdraw(@NotEmpty String merchantNo, @NotEmpty String channel) {
        merchantService.deleteChannel(merchantNo, channel);
        return merchantNo;
    }

    @Override
    public List<String> getConfigWithdraw(@NotEmpty String merchantNo) {
        return merchantService.getChannel(merchantNo);
    }
}
