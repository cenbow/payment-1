/*
 * Project: Payment
 * Document: AbstractGenericService
 * Date: 2020/5/27 10:46 上午
 * Author: wangbz
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.service.impl;

import com.ixiachong.commons.etc.container.BeanFinder;
import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import com.ixiachong.commons.jpa.AbstractEditedEntity;
import com.ixiachong.platform.commons.payment.service.AccountService;
import com.ixiachong.platform.ms.payment.core.exceptions.Errors;
import com.ixiachong.platform.ms.payment.core.exceptions.TradeException;
import com.ixiachong.platform.ms.payment.core.trade.TradeRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * 抽象通用服务类
 *
 * @author wangbz
 */
public abstract class AbstractGenericService {
    protected BeanFinder beanFinder;

    protected AccountService accountService;

    @Autowired
    public void setBeanFinder(BeanFinder beanFinder) {
        this.beanFinder = beanFinder;
    }

    @Autowired
    public void setCustomerFeignClient(AccountService accountService) {
        this.accountService = accountService;
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

    protected <T> T checkBizContent(TradeRequest<T> request) throws TradeException {
        if (request.getBizContent() == null) {
            Errors.PARAMETERS_ERROR.throwException(TradeException.class);
        }
        return request.getBizContent();
    }
}
