/*
 * Project: Payment
 * Document: IdentityKeyDao
 * Date: 2020/7/23 7:53 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.crypto.dao;

import com.ixiachong.platform.ms.payment.crypto.model.IdentityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityKeyDao extends JpaRepository<IdentityKey, String> {
    IdentityKey getFirstByIdentityAndSceneAndAndKeyType(String identity, String scene, String type);
}
