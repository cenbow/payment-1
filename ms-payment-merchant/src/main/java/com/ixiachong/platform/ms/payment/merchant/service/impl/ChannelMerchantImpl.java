/**
 * Project: parent
 * Document: ChannelMerchantImpl
 * Date: 2020/8/19 16:31
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.service.impl;

import com.ixiachong.platform.ms.payment.merchant.dao.ChannelMerchantConfigurationDao;
import com.ixiachong.platform.ms.payment.merchant.model.ChannelMerchantConfiguration;
import com.ixiachong.platform.ms.payment.merchant.service.ChannelMerchantService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Log
@Service
@Transactional
public class ChannelMerchantImpl extends AbstractService implements ChannelMerchantService {
    private ChannelMerchantConfigurationDao dao;

    @Autowired
    public void setDao(ChannelMerchantConfigurationDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Map<String, String> getChannel(String merchantNo, String channel) {
        return merchantInvoke(merchantNo, mid -> dao.findByOwnerIdAndClassify(mid, channel).stream()
                .collect(Collectors.toMap(ChannelMerchantConfiguration::getKey, ChannelMerchantConfiguration::getValue)));
    }

    @Override
    public Map<String, String> createChannelMerchantsConfig(String channel, Map<String, String> configMap) {

        return null;
    }
}
