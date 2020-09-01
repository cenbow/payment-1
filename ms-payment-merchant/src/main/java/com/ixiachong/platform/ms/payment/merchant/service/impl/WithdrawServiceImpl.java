/*
 * Project: Payment
 * Document: WithdrawServiceImpl
 * Date: 2020/8/13 3:59 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.service.impl;

import com.ixiachong.platform.ms.payment.merchant.Const;
import com.ixiachong.platform.ms.payment.merchant.dao.WithdrawConfigurationDao;
import com.ixiachong.platform.ms.payment.merchant.exceptions.ConfigureException;
import com.ixiachong.platform.ms.payment.merchant.exceptions.Errors;
import com.ixiachong.platform.ms.payment.merchant.model.Merchant;
import com.ixiachong.platform.ms.payment.merchant.model.WithdrawConfiguration;
import com.ixiachong.platform.ms.payment.merchant.service.WithdrawService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log
@Service
@Transactional
public class WithdrawServiceImpl extends AbstractService implements WithdrawService {
    private WithdrawConfigurationDao dao;

    @Autowired
    public void setDao(WithdrawConfigurationDao dao) {
        this.dao = dao;
    }

    @Override
    public void createChannel(String merchantNo, String channel, Map<String, String> configs) throws ConfigureException {
        // 获取商户信息
        Merchant merchant = merchantDao.findByNo(merchantNo).orElseThrow(
                () -> Errors.MERCHANT_NO_NOT_EXIST.exception(ConfigureException.class));
        // 检查商户是否开通提现功能
        if (!merchantConfigurationDao.existsByMerchantAndKeyAndValueAndClassifyIsNull(
                merchant, Const.CONFIGURATION_SERVICE, Const.SERVICE_WITHDRAW)) {
            Errors.SERVICE_NOT_OPEN.throwException(ConfigureException.class);
        }
        // 判断商户是否已经开通过对应渠道功能
        if (dao.existsByMerchantAndKeyAndValueAndClassifyIsNull(merchant, Const.CONFIGURATION_CHANNEL, channel)) {
            Errors.WITHDRAW_CHANNEL_EXIST.throwException(ConfigureException.class);
        }

        String mid = merchant.getId();

        WithdrawConfiguration config = setCreatedEntityProperties(new WithdrawConfiguration());
        config.setOwnerId(mid);
        config.setMerchant(merchant);
        config.setClassify(null);
        config.setKey(Const.CONFIGURATION_CHANNEL);
        config.setValue(channel);
        dao.save(config);

        dao.saveAll(configs.entrySet().stream().map(entry -> {
            WithdrawConfiguration conf = setCreatedEntityProperties(new WithdrawConfiguration());
            conf.setOwnerId(mid);
            conf.setMerchant(merchant);
            conf.setClassify(channel);
            conf.setKey(entry.getKey());
            conf.setValue(entry.getValue());
            return conf;
        }).collect(Collectors.toList()));
    }

    @Override
    public void deleteChannel(String merchantNo, String channel) {
        merchantInvoke(merchantNo, mid -> {
            dao.deleteByOwnerIdAndKeyAndValueAndClassifyIsNull(mid, Const.CONFIGURATION_CHANNEL, channel);
            dao.deleteByOwnerIdAndClassify(mid, channel);
            return null;
        });
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> getChannels(String merchantNo) {
        return merchantInvoke(merchantNo, dao::getChannels);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, String> getChannel(String merchantNo, String channel) {
        return merchantInvoke(merchantNo, mid -> dao.findAllByOwnerIdAndClassify(mid, channel).stream()
                .collect(Collectors.toMap(WithdrawConfiguration::getKey, WithdrawConfiguration::getValue)));
    }
}
