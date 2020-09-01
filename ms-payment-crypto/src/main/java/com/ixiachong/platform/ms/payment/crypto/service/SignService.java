/*
 * Project: Payment
 * Document: SignService
 * Date: 2020/7/23 8:24 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.crypto.service;

import java.util.Map;

public interface SignService {
    boolean verify(String type, String identity, String algorithm, String sign, Map<String, String> params);

    String sign(String type, String identity, String algorithm, Map<String, String> params);
}
