/*
 * Project: Payment
 * Document: MerchantManager
 * Date: 2020/8/18 5:07 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.domain;

import lombok.Builder;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Builder
@Log4j2
public class MerchantManager {

    private Function<String, Map<String, String>> configFinder;

    private final Map<String, Merchant> merchantConfigOfNo = new ConcurrentHashMap<>();

    public Merchant get(String no) {

        return merchantConfigOfNo.computeIfAbsent(no, merchantNo -> new Merchant(no, configFinder.apply(merchantNo)));
    }
}
