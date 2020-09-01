/*
 * Project: Payment
 * Document: AbstractTradeFilter
 * Date: 2020/7/24 4:01 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic.filter;

import com.ixiachong.platform.commons.payment.service.CryptoService;
import lombok.Setter;

@Setter
public class AbstractTradeFilter {
    protected CryptoService cryptoService;
}
