/*
 * Project: Accounts
 * Document: AbstractService
 * Date: 2020/7/16 4:03 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.service.impl;

import com.ixiachong.commons.jpa.AbstractCreatedEntity;
import com.ixiachong.commons.jpa.AbstractEditedEntity;
import com.ixiachong.platform.commons.payment.service.MerchantService;
import com.ixiachong.platform.ms.payment.wx.dao.EntPayDao;
import com.ixiachong.platform.ms.payment.wx.dao.EntPayErrorDao;
import com.ixiachong.platform.ms.payment.wx.dao.EntPayRequestDao;
import com.ixiachong.platform.ms.payment.wx.dao.EntPayResponseDao;
import com.ixiachong.platform.ms.payment.wx.dao.EntPayThirdRequestDao;
import com.ixiachong.platform.ms.payment.wx.dao.EntPayThirdResponseDao;
import com.ixiachong.platform.ms.payment.wx.domain.MerchantManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

/**
 * 抽象通用服务类
 *
 * @author wangcy
 */
public abstract class AbstractService {

    @Autowired
    protected MerchantManager merchantManager;

    @Autowired
    protected MerchantService merchantService;


    protected final String FORMAT_DATE_TIME_PATTEN = "yyyy-MM-dd HH:mm:ss";

    protected EntPayDao entPayDao;

    protected EntPayErrorDao entPayErrorDao;
    protected EntPayRequestDao entPayRequestDao;
    protected EntPayResponseDao entPayResponseDao;
    protected EntPayThirdRequestDao entPayThirdRequestDao;
    protected EntPayThirdResponseDao entPayThirdResponseDao;

    @Autowired
    public void setEntPayErrorDao(EntPayErrorDao entPayErrorDao) {
        this.entPayErrorDao = entPayErrorDao;
    }
    @Autowired
    public void setEntPayRequestDao(EntPayRequestDao entPayRequestDao) {
        this.entPayRequestDao = entPayRequestDao;
    }
    @Autowired
    public void setEntPayResponseDao(EntPayResponseDao entPayResponseDao) {
        this.entPayResponseDao = entPayResponseDao;
    }
    @Autowired
    public void setEntPayThirdRequestDao(EntPayThirdRequestDao entPayThirdRequestDao) {
        this.entPayThirdRequestDao = entPayThirdRequestDao;
    }
    @Autowired
    public void setEntPayThirdResponseDao(EntPayThirdResponseDao entPayThirdResponseDao) {
        this.entPayThirdResponseDao = entPayThirdResponseDao;
    }

    @Autowired
    public void setEntPayDao(EntPayDao entPayDao) {
        this.entPayDao = entPayDao;
    }

    protected <T extends AbstractEditedEntity> T setEntityProperties(T entity) {
        entity.setState("A");
        entity.setUpdateTime(new Date());
        return  entity;
    }

    protected <T extends AbstractCreatedEntity> T setCreatedEntityProperties(T entity) {
        entity.setId(newId());
        entity.setCreateTime(new Date());
        return entity;
    }

    protected String newId() {
        return UUID.randomUUID().toString();
    }

}