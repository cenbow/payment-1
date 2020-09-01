/*
 * Project: Payment
 * Document: Request
 * Date: 2020/8/7 7:24 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeRequest<T> {
    private String appId;
    private String requestId;
    private String method;
    private String format;
    private String charset;
    private String signType;
    private String sign;
    private String timestamp;
    private String notifyUrl;
    private String attach;
    private T bizContent;
}
