package com.ixiachong.platform.ms.payment.wx.service;

import com.ixiachong.platform.commons.payment.request.EnterprisePaymentRequest;
import com.ixiachong.platform.commons.payment.response.EnterprisePayment;
import com.ixiachong.platform.commons.payment.response.EnterprisePaymentResponse;
import com.ixiachong.platform.ms.payment.wx.exceptions.PaymentException;

/**
 * Project: parent
 * Document: EntPayService
 * Date: 2020/8/4 14:40
 * @Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public interface EntPayService {

    EnterprisePaymentResponse transfers(EnterprisePaymentRequest request) throws PaymentException;

    EnterprisePayment queryEntPay(String no,String merchantNo, String type) throws PaymentException;


}
