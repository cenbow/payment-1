/**
 * Project: parent
 * Document: TransfersServiceImpl
 * Date: 2020/8/4 14:41
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.controller;


import com.ixiachong.platform.ms.payment.alipay.service.EntPayService;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractController {
    protected EntPayService entPayService;

    @Autowired
    public void setEntPayService(EntPayService entPayService) {
        this.entPayService = entPayService;
    }

}
