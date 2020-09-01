/*
 * Project: Accounts
 * Document: MerchantResponse
 * Date: 2020/8/13 20:05
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.response;

import com.ixiachong.platform.commons.payment.dto.Merchant;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author fengzl
 * @Date 2020/8/13
 */
@Setter
@Getter
public class MerchantResponse extends Merchant {
    private List<String> services;
}
