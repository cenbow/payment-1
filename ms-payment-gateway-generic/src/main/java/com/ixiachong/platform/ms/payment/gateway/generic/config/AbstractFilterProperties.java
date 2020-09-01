/*
 * Project: Payment
 * Document: AbstractFilterProperties
 * Date: 2020/7/23 10:33 上午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractFilterProperties {
    private int order = -201;
}
