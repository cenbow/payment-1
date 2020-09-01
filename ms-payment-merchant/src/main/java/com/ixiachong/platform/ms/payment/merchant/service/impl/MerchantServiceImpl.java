/*
 * Project: Payment
 * Document: MerchantServiceImpl
 * Date: 2020/8/13 3:46 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.service.impl;

import com.ixiachong.platform.commons.payment.service.AccountService;
import com.ixiachong.platform.ms.payment.merchant.Const;
import com.ixiachong.platform.ms.payment.merchant.dao.MerchantConfigurationDao;
import com.ixiachong.platform.ms.payment.merchant.exceptions.Errors;
import com.ixiachong.platform.ms.payment.merchant.exceptions.MerchantException;
import com.ixiachong.platform.ms.payment.merchant.model.Merchant;
import com.ixiachong.platform.ms.payment.merchant.model.MerchantConfiguration;
import com.ixiachong.platform.ms.payment.merchant.service.MerchantService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
@Service
@Validated
public class MerchantServiceImpl extends AbstractService implements MerchantService {
    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    private MerchantConfigurationDao dao;

    @Autowired
    public void setDao(MerchantConfigurationDao dao) {
        this.dao = dao;
    }

    @Override
    public Merchant createMerchant(@NotEmpty String customerNo, @NotEmpty String accountNo, String... services) throws MerchantException {
        if (!accountService.verification(accountNo, customerNo)) {
            Errors.CUSTOMER_ACCOUNT_ERROR.throwException(MerchantException.class);
        }

        if (merchantDao.existsByAccountNo(accountNo)) {
            Errors.MERCHANT_NO_EXISTS.throwException(MerchantException.class);
        }

        Merchant merchant = setEditedEntityProperties(new Merchant());
        merchant.setNo(newId().replaceAll("-", ""));
        merchant.setAccountNo(accountNo);
        merchant.setCustomerNo(customerNo);
        Merchant saveMerchant = merchantDao.save(merchant);

        if (ArrayUtils.isNotEmpty(services)) {
            dao.saveAll(Stream.of(services).map(service -> {
                MerchantConfiguration configuration = new MerchantConfiguration();
                configuration.setOwnerId(saveMerchant.getId());
                configuration.setMerchant(saveMerchant);
                configuration.setKey(Const.CONFIGURATION_SERVICE);
                configuration.setValue(service);
                return setEditedEntityProperties(configuration);
            }).collect(Collectors.toList()));
        }

        return saveMerchant;
    }

    @Override
    public void disableMerchant(String no) {

    }

    @Override
    public Merchant getMerchant(String no) {
        return merchantDao.findByNo(no).orElse(null);
    }

    @Override
    public List<String> getMerchantServices(String no) {
        return merchantInvoke(no, dao::getServices);
    }
}
