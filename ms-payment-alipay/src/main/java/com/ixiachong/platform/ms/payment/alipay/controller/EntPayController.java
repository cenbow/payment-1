/**
 * Project: parent
 * Document: WxPayController
 * Date: 2020/8/4 18:07
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.controller;

import com.ixiachong.platform.commons.payment.request.EnterprisePaymentRequest;
import com.ixiachong.platform.commons.payment.response.EnterprisePayment;
import com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse;
import com.ixiachong.platform.ms.payment.alipay.exceptions.PaymentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: wangcy
 */
@RequestMapping("/api/enterprise-payments")
@RestController
public class EntPayController extends AbstractController{

    @PostMapping
    public EnterprisePaymentResponse transfers(@RequestBody @Valid EnterprisePaymentRequest enterprisePaymentRequest) throws PaymentException {

        return entPayService.transfers(enterprisePaymentRequest);
    }

    @GetMapping("/{no}")
    public EnterprisePayment queryEntPay(@PathVariable String no,@RequestParam(value = "merchantNo") String merchantNo,
        @RequestParam(value = "type") String type) throws PaymentException {

        return entPayService.queryEntPay(no,merchantNo, type);
    }
}
