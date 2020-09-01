/*
 * Project: Accounts
 * Document: WxPayService
 * Date: 2020/8/10 11:32
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author fengzl
 * @Date 2020/8/10
 */
@FeignClient(value = "ms-payment-alipay", path = "/api/enterprise-payments")
public interface AliPayService extends WithdrawChannels {

}
