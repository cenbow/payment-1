/*
 * Project: Payment
 * Document: SystemService
 * Date: 2020/7/23 8:02 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.crypto.service;

import com.ixiachong.platform.ms.payment.crypto.model.IdentityKey;

public interface KeyService {
    /**
     * 获取指定标识在特定场景下的指定类型key
     *
     * @param identity 标识
     * @param scene    场景
     * @param type     类别
     * @return
     */
    IdentityKey getKey(String identity, String scene, String type);
}
