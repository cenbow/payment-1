/*
 * Project: Accounts
 * Document: AbstractService
 * Date: 2020/7/16 4:03 下午
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.service.impl;

import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import com.ixiachong.commons.jpa.AbstractEditedEntity;
import com.ixiachong.platform.ms.payment.merchant.dao.MerchantConfigurationDao;
import com.ixiachong.platform.ms.payment.merchant.dao.MerchantDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

/**
 * 抽象通用服务类
 *
 * @author fengzl
 */
public abstract class AbstractService {
    protected MerchantDao merchantDao;
    protected MerchantConfigurationDao merchantConfigurationDao;

    @Autowired
    public void setMerchantConfigurationDao(MerchantConfigurationDao merchantConfigurationDao) {
        this.merchantConfigurationDao = merchantConfigurationDao;
    }

    @Autowired
    public void setMerchantDao(MerchantDao merchantDao) {
        this.merchantDao = merchantDao;
    }

    protected <T extends AbstractEditedEntity> T setEditedEntityProperties(T entity) {
        entity.setState("A");
        return setCreatedEntityProperties(entity);
    }

    protected <T extends AbstractCreatedEntity> T setCreatedEntityProperties(T entity) {
        entity.setId(newId());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(entity.getCreateTime());
        return entity;
    }

    protected String newId() {
        return UUID.randomUUID().toString();
    }

    protected <R> R merchantInvoke(String merchantNo, Function<String, R> func) {
        String mid = merchantDao.getIdByNo(merchantNo);
        if (StringUtils.isBlank(mid)) {
            return null;
        } else {
            return func.apply(mid);
        }
    }
}