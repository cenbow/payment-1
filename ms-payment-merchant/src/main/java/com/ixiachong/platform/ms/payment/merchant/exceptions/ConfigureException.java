/*
 * Project: Payment
 * Document: ConfigationException
 * Date: 2020/8/13 4:18 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.exceptions;

import com.ixiachong.commons.etc.exceptions.BaseException;

public class ConfigureException extends BaseException {
    public ConfigureException(String code) {
        super(code);
    }

    public ConfigureException(String message, String code) {
        super(message, code);
    }

    public ConfigureException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }

    public ConfigureException(Throwable cause, String code) {
        super(cause, code);
    }
}
