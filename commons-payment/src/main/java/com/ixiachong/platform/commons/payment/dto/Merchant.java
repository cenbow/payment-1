/*
 * Project: Accounts
 * Document: Merchant
 * Date: 2020/8/13 20:04
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author fengzl
 * @Date 2020/8/13
 */
@Setter
@Getter
public class Merchant extends BaseMerchant{
    /**
     * 客户编号
     */
    private String customerNo;
}
