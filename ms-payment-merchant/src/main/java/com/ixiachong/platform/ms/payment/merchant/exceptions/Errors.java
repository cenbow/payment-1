/*
 * Project: Payment
 * Document: Errors
 * Date: 2020/8/10 8:49 上午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.exceptions;

import com.ixiachong.commons.etc.exceptions.BaseException;
import com.ixiachong.platform.ms.payment.merchant.Const;
import lombok.extern.java.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

@Log
public enum Errors {
    CUSTOMER_ACCOUNT_ERROR(Const.CUSTOMER_ACCOUNT_ERROR, "客户编号与账号不符"),
    MERCHANT_NO_EXISTS(Const.MERCHANT_NO_EXISTS, "该帐号已创建商户号"),
    MERCHANT_NO_NOT_EXIST(Const.MERCHANT_NO_NOT_EXIST, "商户号不存在"),
    WITHDRAW_CHANNEL_EXIST(Const.WITHDRAW_CHANNEL_EXIST, "渠道配置已经存在"),
    SERVICE_NOT_OPEN(Const.SERVICE_NOT_OPEN, "服务未开通"),
    ;
    private final String code;
    private final String message;

    private static final Class[] DEFAULT_CONSTRUCTOR_PARAMETER_TYPES = new Class[]{String.class, String.class};

    Errors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseException exception() {
        return exception(BaseException.class, null);
    }

    public BaseException exception(Object additional) {
        return exception(BaseException.class, additional);
    }

    public <E extends BaseException> E exception(Class<E> clazz) {
        return exception(clazz, null);
    }

    public <E extends BaseException> E exception(Class<E> clazz, Object additional) {
        try {
            E e = clazz.getConstructor(DEFAULT_CONSTRUCTOR_PARAMETER_TYPES).newInstance(message, code);
            if (additional != null) {
                e.setAdditional(additional);
            }
            return e;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            log.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    public void throwException() throws BaseException {
        throwException(BaseException.class, null);
    }

    public void throwException(Object additional) throws BaseException {
        throwException(BaseException.class, additional);
    }

    public <E extends BaseException> void throwException(Class<E> clazz) throws E {
        throwException(clazz, null);
    }

    public <E extends BaseException> void throwException(Class<E> clazz, Object additional) throws E {
        E e = exception(clazz, additional);
        if (e != null) {
            throw e;
        }
    }
}
