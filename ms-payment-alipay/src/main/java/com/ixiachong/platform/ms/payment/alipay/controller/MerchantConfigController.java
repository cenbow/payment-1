/**
 * Project: parent
 * Document: CogfigController
 * Date: 2020/8/21 15:01
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.controller;

import com.ixiachong.platform.ms.payment.alipay.domain.MerchantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: wangcy
 */
@RestController
@RequestMapping("/api/merchant-config")
public class MerchantConfigController {

    private MerchantManager merchantManager;

    @Autowired
    public void setMerchantManager(MerchantManager merchantManager) {
        this.merchantManager = merchantManager;
    }

    @PostMapping("{no}")
    public void releaseMerchantConfig(@PathVariable String no){
        merchantManager.release(no);
    }

}
