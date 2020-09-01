/*
 * Project: Accounts
 * Document: MerchantCreateRequest
 * Date: 2020/8/17 20:04
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.request;

import lombok.Data;

/**
 * @Author fengzl
 * @Date 2020/8/17
 */
@Data
public class MerchantCreateRequest {
    private String accountNo;

    private String[] services;
}
