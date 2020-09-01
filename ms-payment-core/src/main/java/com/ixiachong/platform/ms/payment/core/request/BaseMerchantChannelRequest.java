/*
 * Project: Accounts
 * Document: BaseMerchantChannelRequest
 * Date: 2020/8/18 14:18
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author fengzl
 * @Date 2020/8/18
 */
@Getter
@Setter
public class BaseMerchantChannelRequest extends BaseMerchantRequest {
    private String channel;
}
