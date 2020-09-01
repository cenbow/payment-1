/**
 * Project: parent
 * Document: ChannelMerchantService
 * Date: 2020/8/19 16:31
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.service;

import java.util.Map;

public interface ChannelMerchantService {

    /**
     * 通过商户号和渠道获取配置
     * @param merchantNo
     * @param channel
     * @return
     */
    Map<String, String> getChannel(String merchantNo, String channel);

    Map<String, String> createChannelMerchantsConfig( String channel, Map<String,String> configMap);

}
