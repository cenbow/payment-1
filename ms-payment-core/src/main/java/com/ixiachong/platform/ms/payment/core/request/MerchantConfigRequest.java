/*
 * Project: Accounts
 * Document: MerchantConfigRequest
 * Date: 2020/8/17 20:24
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @Author fengzl
 * @Date 2020/8/17
 */
@Setter
@Getter
public class MerchantConfigRequest extends BaseMerchantRequest {

    private Map<String, Map<String, String>> channels;
}
