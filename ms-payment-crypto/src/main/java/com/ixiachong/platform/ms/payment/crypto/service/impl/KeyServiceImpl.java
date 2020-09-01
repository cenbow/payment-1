/*
 * Project: Payment
 * Document: SystemServiceImpl
 * Date: 2020/7/23 8:05 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.crypto.service.impl;

import com.ixiachong.platform.ms.payment.crypto.dao.IdentityKeyDao;
import com.ixiachong.platform.ms.payment.crypto.model.IdentityKey;
import com.ixiachong.platform.ms.payment.crypto.service.KeyService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Log
@Service
public class KeyServiceImpl implements KeyService {
    private IdentityKeyDao keyDao;

    @Autowired
    public void setKeyDao(IdentityKeyDao keyDao) {
        this.keyDao = keyDao;
    }

    @Override
    @Cacheable("key-cache")
    public IdentityKey getKey(String identity, String scene, String type) {
        if (log.isLoggable(Level.FINE)) {
            log.fine(String.format("get key: %s, %s, %s", identity, scene, type));
        }
        return keyDao.getFirstByIdentityAndSceneAndAndKeyType(identity, scene, type);
    }
}
