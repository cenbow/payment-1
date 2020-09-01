/*
 * Project: Accounts
 * Document: Errors
 * Date: 2020/8/7 15:49 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.exceptions;

import com.ixiachong.commons.etc.exceptions.BaseException;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * @author wangzcy
 *
 */
@Log
public enum Errors {

    //支付异常处理
    PAYMENT_CALLING_FAILED("PAYMENT_CALLING_FAILED", "支付接口调用异常!"),

    PAYMENT_ORDER_AMOUNT_CHANGE("PAYMENT_ORDER_AMOUNT_CHANGE", "订单金额发生变化!"),

    PAYMENT_ORDER_NOT_EXISTS("PAYMENT_ORDER_NOT_EXISTS", "支付订单不存在!"),

    PAYMENT_ACCOUNT_NOT_EXISTS("PAYMENT_ACCOUNT_NOT_EXISTS", "商户用户不存在!"),

    PAYMENT_ACCOUNT_IS_CHANGED("PAYMENT_ACCOUNT_IS_CHANGED","商户用户改变!"),

    PAYMENT_OPENID_ERROR("PAYMENT_OPENID_ERROR", "OPENID_ERROR"),

    PAYMENT_NOT_ENOUGH("NOTENOUGH", "NOTENOUGH"),

    PAYMENT_SEND_FAILED("PAYMENT_SEND_FAILED", "SEND_FAILED"),

    PAYMENT_SIGN_ERROR("PAYMENT_SIGN_ERROR", "SIGN_ERROR"),

    PAYMENT_FREQ_LIMIT("PAYMENT_FREQ_LIMIT", "FREQ_LIMIT"),

    PAYMENT_MONEY_LIMIT("PAYMENT_MONEY_LIMIT", "MONEY_LIMIT"),

    PAYMENT_V2_ACCOUNT_SIMPLE_BAN("PAYMENT_V2_ACCOUNT_SIMPLE_BAN", "V2_ACCOUNT_SIMPLE_BAN"),

    PAYMENT_SENDNUM_LIMIT("PAYMENT_SENDNUM_LIMIT", "SENDNUM_LIMIT"),

    PAYMENT_PARAM_ERROR("PAYMENT_PARAM_ERROR", "PARAM_ERROR");


    private String code;
    private String message;

    public static Errors getErrors(String wxErrorCode){

       return Arrays.stream(Errors.values())
               .filter(item->item.message.equals(wxErrorCode) || item.code.equals(wxErrorCode))
               .findFirst()
               .orElse(null);
    }

    Errors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
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
            E e = clazz.getConstructor(String.class, String.class).newInstance(message, code);
            log.warning("================>"+ e.getCode());
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
        throwExceptions(BaseException.class, null);
    }

    public void throwException(Object additional) throws BaseException {
        throwExceptions(BaseException.class, additional);
    }

    public <E extends BaseException> void throwException(Class<E> clazz) throws E {
        throwExceptions(clazz, null);
    }

    public <E extends BaseException> void throwException(Class<E> clazz,String message) throws E {
        if (StringUtils.isNotEmpty(message)){
            this.message = message;
        }
        throwExceptions(clazz, null);
    }

    public <E extends BaseException> void throwExceptions(Class<E> clazz, Object additional) throws E {
        E e = exception(clazz, additional);
        if (e != null) {
            throw e;
        }
    }
}
