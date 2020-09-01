/*
 * Project: Payment
 * Document: SignServiceImpl
 * Date: 2020/7/23 8:24 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.crypto.service.impl;

import com.ixiachong.platform.commons.payment.util.SignUtils;
import com.ixiachong.platform.ms.payment.crypto.model.IdentityKey;
import com.ixiachong.platform.ms.payment.crypto.service.KeyService;
import com.ixiachong.platform.ms.payment.crypto.service.SignService;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.logging.Level;

@Log
@Service
public class SignServiceImpl implements SignService {
    private KeyService keyService;

    @Autowired
    public void setKeyService(KeyService keyService) {
        this.keyService = keyService;
    }

    @Override
    public boolean verify(String type, String identity, String algorithm, String sign, Map<String, String> params) {
        if (StringUtils.isBlank(sign)) {
            log.warning("sign is blank.");
            return false;
        }

        String result = this.sign(type, identity, algorithm, params);

        return StringUtils.isNotBlank(result) && StringUtils.equals(sign, result);
    }

    @Override
    public String sign(String type, String identity, String algorithm, Map<String, String> params) {
        IdentityKey key = keyService.getKey(identity, type, algorithm);
        if (key == null) {
            log.warning(String.format("not found key of: %s %s %s", identity, type, algorithm));
            return null;
        }

        String result = SignUtils.sign(params, key.getKeyValue(), "UTF-8");
        if (log.isLoggable(Level.FINE)) {
            log.fine(String.format("calculate sign: %s", result));
        }

        return result;
    }
}
