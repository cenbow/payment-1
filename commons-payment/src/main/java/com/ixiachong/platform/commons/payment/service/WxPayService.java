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

import com.ixiachong.platform.commons.payment.response.EnterprisePayment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author fengzl
 * @Date 2020/8/10
 */
@FeignClient(value = "ms-payment-wxpay", path = "/api/enterprise-payments")
public interface WxPayService extends WithdrawChannels {
    @GetMapping("/{no}")
    EnterprisePayment queryEntPay(@PathVariable String no, @RequestParam(value = "merchantNo") String merchantNo,
                                  @RequestParam(value = "type") String type);
}
