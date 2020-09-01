/*
 * Project: Payment
 * Document: Errors
 * Date: 2020/8/10 8:49 上午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.exceptions;

import com.ixiachong.commons.etc.exceptions.BaseException;
import com.ixiachong.platform.ms.payment.core.Const;
import lombok.extern.java.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

@Log
public enum Errors {
    UNKNOWN(Const.UNKNOWN, "未知异常"),
    TRADE_HANDLER_NOT_FOUND(Const.EC_TRADE_HANDLER_NOT_FOUND, "没有找到对应的交易处理类"),
    WITHDRAW_CHANNEL_NOT_EXISTS(Const.WITHDRAW_CHANNEL_NOT_EXISTS, "提现渠道不存在"),
    WITHDRAW_NOT_PROCESSING(Const.WITHDRAW_NOT_PROCESSING, "提现状态不是处理中"),
    MERCHANT_NOT_CONFIG(Const.MERCHANT_NOT_CONFIG, "创建参数不合法，配置信息不能为空"),
    MERCHANT_NOT_EXISTS(Const.MERCHANT_NOT_EXISTS, "商户不存在"),
    CUSTOMER_NOT_EXISTS(Const.CUSTOMER_NOT_EXISTS, "客户不存在"),
    CUSTOMER_TYPE_INVALID(Const.CUSTOMER_TYPE_INVALID, "无效的客户类型"),
    WITHDRAW_FAILURE(Const.WITHDRAW_FAILURE, "提现失败"),
    NOT_ALLOW_WITHDRAW(Const.NOT_ALLOW_WITHDRAW, "该商户号没有提现权限"),
    OUT_TRADE_NO_EXISTS(Const.OUT_TRADE_NO_EXISTS, "商户单号已存在"),
    WITHDRAW_NOT_ENOUGH_MIN(Const.WITHDRAW_NOT_ENOUGH_MIN, "提现金额不足10元"),
    WITHDRAW_APP_MERCHANT_NOT_EXISTS(Const.WITHDRAW_APP_MERCHANT_NOT_EXISTS, "该商户号平台未配置对应的提款渠道"),
    ACCOUNT_UNUSABLE(Const.ACCOUNT_UNUSABLE, "账户处于不可用状态"),
    REQUEST_BIZ_INCOMPLETE(Const.REQUEST_BIZ_INCOMPLETE, "请求参数缺少必要信息"),
    PARAMETERS_ERROR(Const.PARAMETERS_ERROR, "请求参数不合法"),
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
