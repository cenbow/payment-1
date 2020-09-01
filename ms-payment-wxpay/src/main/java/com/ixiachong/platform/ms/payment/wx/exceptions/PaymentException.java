/*
 * Project: Accounts
 * Document: PaymentException
 * Date: 2020/8/7 16:05 下午
 * Author: wangcy
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.exceptions;

import com.ixiachong.commons.etc.exceptions.BaseException;

/**
 * @Author wangcy
 * 支付异常
 */
public class PaymentException extends BaseException {

    public PaymentException(String message, String code) {
        super(message, code);
    }

    public PaymentException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }

    public PaymentException(Throwable cause, String code) {
        super(cause, code);
    }
}
