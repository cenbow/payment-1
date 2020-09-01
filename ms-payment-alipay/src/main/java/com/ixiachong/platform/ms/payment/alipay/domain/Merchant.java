/*
 * Project: Payment
 * Document: Merchant
 * Date: 2020/8/18 5:07 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.domain;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import lombok.extern.java.Log;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;


@Log
public class Merchant {
    private String no;
    private Map<String, String> configs;
    private AlipayClient client;
    private Function<String, String> getLocalPath;
    private Function<String, String> readContent;

    Merchant(String no, Map<String, String> configs, Function<String, String> getLocalPath, Function<String, String> readContent) {
        this.no = no;
        this.configs = configs;
        this.getLocalPath = getLocalPath;
        this.readContent = readContent;
    }

    public AlipayClient getClient() {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    CertAlipayRequest request = new CertAlipayRequest();
                    configs.entrySet().stream()
                            .filter(entry -> entry.getValue() != null)
                            .forEach(entry -> {
                                try {
                                    PropertyUtils.setProperty(request, entry.getKey(), entry.getValue());
                                } catch (Exception ex) {
                                    log.log(Level.WARNING, ex.getMessage(), ex);
                                }
                            });

                    request.setPrivateKey(this.readContent.apply(request.getPrivateKey()));
                    request.setCertPath(this.getLocalPath.apply(request.getCertPath()));
                    request.setAlipayPublicCertPath(this.getLocalPath.apply(request.getAlipayPublicCertPath()));
                    request.setRootCertPath(this.getLocalPath.apply(request.getRootCertPath()));

                    try {
                        this.client = new DefaultAlipayClient(request);
                    } catch (AlipayApiException e) {
                        log.log(Level.SEVERE, e.getMessage(), e);
                        this.client = null;
                    }
                }
            }
        }
        return client;
    }

}
