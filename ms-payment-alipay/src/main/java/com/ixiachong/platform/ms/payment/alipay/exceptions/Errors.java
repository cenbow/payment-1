/*
 * Project: Accounts
 * Document: Errors
 * Date: 2020/8/7 15:49 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.exceptions;

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

    PAYMENT_ACCOUNT_IS_CHANGED("PAYMENT_ACCOUNT_IS_CHANGED", "商户用户改变!"),

    PAYMENT_INVALID_PARAMETER("PAYMENT_INVALID_PARAMETER", "INVALID_PARAMETER"),

    PAYMENT_SYSTEM_ERROR("PAYMENT_SYSTEM_ERROR", "SYSTEM_ERROR"),

    PAYMENT_EXCEED_LIMIT_SM_AMOUNT("PAYMENT_EXCEED_LIMIT_SM_AMOUNT", "EXCEED_LIMIT_SM_AMOUNT"),

    PAYMENT_EXCEED_LIMIT_MM_AMOUNT("PAYMENT_EXCEED_LIMIT_MM_AMOUNT", "EXCEED_LIMIT_MM_AMOUNT"),

    PAYMENT_PAYCARD_UNABLE_PAYMENT("PAYMENT_PAYCARD_UNABLE_PAYMENT", "PAYCARD_UNABLE_PAYMENT"),

    PAYMENT_PAYER_BALANCE_NOT_ENOUGH("PAYMENT_PAYER_BALANCE_NOT_ENOUGH", "PAYER_BALANCE_NOT_ENOUGH"),

    PAYMENT_PAYMENT_INFO_INCONSISTENCY("PAYMENT_PAYMENT_INFO_INCONSISTENCY", "PAYMENT_INFO_INCONSISTENCY"),

    PAYMENT_PERMIT_CHECK_PERM_IDENTITY_THEFT("PAYMENT_PERMIT_CHECK_PERM_IDENTITY_THEFT", "PERMIT_CHECK_PERM_IDENTITY_THEFT"),

    PAYMENT_REMARK_HAS_SENSITIVE_WORD("PAYMENT_REMARK_HAS_SENSITIVE_WORD", "REMARK_HAS_SENSITIVE_WORD"),

    PAYMENT_EXCEED_LIMIT_DM_AMOUNT("PAYMENT_EXCEED_LIMIT_DM_AMOUNT", "EXCEED_LIMIT_DM_AMOUNT"),

    PAYMENT_NO_ACCOUNT_RECEIVE_PERMISSION("PAYMENT_NO_ACCOUNT_RECEIVE_PERMISSION", "NO_ACCOUNT_RECEIVE_PERMISSION"),

    PAYMENT_BALANCE_IS_NOT_ENOUGH("PAYMENT_BALANCE_IS_NOT_ENOUGH", "BALANCE_IS_NOT_ENOUGH"),

    PAYMENT_PAYMENT_MONEY_NOT_ENOUGH("PAYMENT_PAYMENT_MONEY_NOT_ENOUGH", "PAYMENT_MONEY_NOT_ENOUGH"),

    PAYMENT_TRUSTEESHIP_RECIEVE_QUOTA_LIMIT("PAYMENT_TRUSTEESHIP_RECIEVE_QUOTA_LIMIT", "TRUSTEESHIP_RECIEVE_QUOTA_LIMIT"),

    PAYMENT_SECURITY_CHECK_FAILED("PAYMENT_SECURITY_CHECK_FAILED", "SECURITY_CHECK_FAILED"),

    PAYMENT_EXCEED_LIMIT_DM_MAX_AMOUNT("PAYMENT_EXCEED_LIMIT_DM_MAX_AMOUNT", "EXCEED_LIMIT_DM_MAX_AMOUNT"),

    PAYMENT_BLOCK_USER_FORBBIDEN_RECIEVE("PAYMENT_BLOCK_USER_FORBBIDEN_RECIEVE", "BLOCK_USER_FORBBIDEN_RECIEVE"),

    PAYMENT_PAYEE_NOT_RELNAME_CERTIFY("PAYMENT_PAYEE_NOT_RELNAME_CERTIFY", "PAYEE_NOT_RELNAME_CERTIFY"),

    PAYMENT_EXCEED_LIMIT_UNRN_DM_AMOUNT("PAYMENT_EXCEED_LIMIT_UNRN_DM_AMOUNT", "EXCEED_LIMIT_UNRN_DM_AMOUNT"),

    PAYMENT_REQUEST_PROCESSING("PAYMENT_REQUEST_PROCESSING", "REQUEST_PROCESSING");

    private String code;
    private String message;


    Errors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Errors getErrors(String wxErrorCode){

        return Arrays.stream(Errors.values())
                .filter(item->item.message.equals(wxErrorCode) || item.code.equals(wxErrorCode))
                .findFirst()
                .orElse(null);
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
            if (additional != null) {
                e.setAdditional(additional);
            }
            log.warning("================>"+ e.getCode());
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
