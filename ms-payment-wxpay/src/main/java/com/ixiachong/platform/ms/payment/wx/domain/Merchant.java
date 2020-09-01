/*
 * Project: Payment
 * Document: Merchant
 * Date: 2020/8/18 5:07 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.domain;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.platform.ms.payment.wx.config.WxPayProperties;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;

import java.util.Map;


@Log
public class Merchant {
    private String no;
    private Map<String, String> configs;
    private WxPayService client;

    Merchant(String no, Map<String, String> configs) {
        this.no = no;
        this.configs = configs;
    }

    public WxPayService getClient() {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    WxPayProperties wxPayProperties = BeanUtils.mapping(configs, WxPayProperties.class);
                    WxPayConfig payConfig = new WxPayConfig();
                    payConfig.setAppId(StringUtils.trimToNull(wxPayProperties.getAppId()));
                    payConfig.setMchId(StringUtils.trimToNull(wxPayProperties.getMchId()));
                    payConfig.setMchKey(StringUtils.trimToNull(wxPayProperties.getMchKey()));
                    payConfig.setKeyPath(StringUtils.trimToNull(wxPayProperties.getKeyPath()));
                    payConfig.setUseSandboxEnv(false);
                    WxPayService wxPayService = new WxPayServiceImpl();
                    wxPayService.setConfig(payConfig);
                    this.client = wxPayService;
                }
            }
        }
        return client;
    }

}
