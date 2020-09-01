/*
 * Project: Payment
 * Document: MerchantManagerTest
 * Date: 2020/8/18 5:35 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay;

import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.platform.ms.payment.alipay.domain.Merchant;
import com.ixiachong.platform.ms.payment.alipay.domain.MerchantManager;
import lombok.extern.java.Log;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;

@Log
public class MerchantManagerTest {
    @Test
    public void test() throws Exception {

        Map<String, Map> configs = MapUtils.map(String.class, Map.class,
                "001", MapUtils.map(
                        "serverUrl", "http://wwww.xxxxx.com",
                        "appId", "1234567",
                        "privateKey", "http://repo.sz.xiac.io/repository/raw/xiac/docs/projects.json",
                        "certPath", "http://repo.sz.xiac.io/repository/raw/xiac/certs/alipay_root.crt"),
                "002", MapUtils.map(
                        "serverUrl", "http://wwww.xxxxx.com",
                        "appId", "23232342",
                        "privateKey", "http://repo.sz.xiac.io/repository/raw/xiac/certs/wxpay_apiclient.p12",
                        "certPath", "http://repo.sz.xiac.io/repository/raw/xiac/certs/alipay_root.crt"),
                "003", MapUtils.map(
                        "serverUrl", "http://wwww.xxxxx.com",
                        "appId", "1234567")
        );

        File directory = new File("/tmp/xiac");
        directory.mkdirs();

        MerchantManager manager = MerchantManager.builder()
                .configFinder(no -> configs.get(no))
                .directory(directory)
                .build();

        Merchant m1 = manager.get("001");
        System.out.println(m1);

        Merchant m2 = manager.get("002");
        System.out.println(m2);

        Merchant m3 = manager.get("001");
        System.out.println(m3);

        try {
            m1.getClient();
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }

        try {
            m2.getClient();
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
        }

        Thread.sleep(10000);
    }
}
