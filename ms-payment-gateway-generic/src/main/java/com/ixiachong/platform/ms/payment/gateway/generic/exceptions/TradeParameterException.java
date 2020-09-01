/*
 * Project: Payment
 * Document: TradeParameterException
 * Date: 2020/7/23 5:10 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic.exceptions;

import com.ixiachong.commons.etc.exceptions.BaseException;

public class TradeParameterException extends BaseException {
    public TradeParameterException(String code) {
        super(code);
    }

    public TradeParameterException(String message, String code) {
        super(message, code);
    }
}
