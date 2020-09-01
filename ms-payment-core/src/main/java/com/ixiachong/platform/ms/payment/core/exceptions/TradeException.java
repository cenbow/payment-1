/*
 * Project: Payment
 * Document: TradeException
 * Date: 2020/8/8 10:43 上午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.exceptions;

import com.ixiachong.commons.etc.exceptions.BaseException;

public class TradeException extends BaseException {
    public TradeException(String code) {
        super(code);
    }

    public TradeException(String message, String code) {
        super(message, code);
    }

    public TradeException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }

    public TradeException(Throwable cause, String code) {
        super(cause, code);
    }
}
