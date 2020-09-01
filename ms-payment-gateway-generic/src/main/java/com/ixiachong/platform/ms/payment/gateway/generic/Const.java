/*
 * Project: Payment
 * Document: Const
 * Date: 2020/7/20 3:53 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic;

public interface Const extends com.ixiachong.platform.commons.payment.Const {
    String TRADE_MESSAGE_FILTER_CONFIG_PREFIX = "application.web.filter.trade";
    String TRADE_RESPONSE_FILTER_CONFIG_PREFIX = "application.web.filter.response";

    String ERROR_CODE = "BASE_EXCEPTION_CODE";
    String ERROR_MESSAGE = "BASE_EXCEPTION_MESSAGE";
    String ERROR_BODY = "BASE_EXCEPTION_BODY";

    String TRANSFER_ENCODING_CHUNKED = "chunked";

    String REQUEST_TRADE_ATTRIBUTE = "_REQUEST_TRADE_ATTRIBUTE";
}
