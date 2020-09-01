/*
 * Project: Payment
 * Document: TradeMessageProperties
 * Date: 2020/7/20 8:01 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic.config;

import com.ixiachong.platform.ms.payment.gateway.generic.Const;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeMessageProperties extends AbstractFilterProperties {
    private String errorCodeKey = Const.ERROR_CODE;
    private String errorMessageKey = Const.ERROR_MESSAGE;
    private String errorBodyKey = Const.ERROR_BODY;
    private boolean verifySign = true;
}
