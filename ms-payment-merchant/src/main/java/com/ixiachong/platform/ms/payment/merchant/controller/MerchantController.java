/*
 * Project: Payment
 * Document: MerchantController
 * Date: 2020/8/12 5:48 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.controller;

import com.ixiachong.commons.beanutils.BeanUtils;
import com.ixiachong.platform.commons.payment.dto.Merchant;
import com.ixiachong.platform.commons.payment.response.MerchantResponse;
import com.ixiachong.platform.ms.payment.merchant.exceptions.Errors;
import com.ixiachong.platform.ms.payment.merchant.exceptions.MerchantException;
import com.ixiachong.platform.ms.payment.merchant.service.MerchantService;
import com.ixiachong.platform.ms.payment.merchant.utils.ModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {
    private MerchantService merchantService;

    @Autowired
    public void setMerchantService(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    /**
     * 创建商户
     *
     * @return
     */
    @PostMapping
    public Merchant create(
            @RequestParam("customerNo") String customerNo,
            @RequestParam("accountNo") String accountNo,
            @RequestParam("services") String[] services) throws MerchantException {

        return ModelUtils.toClient(merchantService.createMerchant(customerNo, accountNo, services), Merchant.class);
    }

    /**
     * 查询指定商户信息
     *
     * @param no 商户号
     * @return
     */
    @GetMapping("{no}")
    public MerchantResponse getOne(@PathVariable String no) throws MerchantException {
        MerchantResponse merchantResponse = BeanUtils.mapping(merchantService.getMerchant(no), MerchantResponse.class);
        if (null == merchantResponse) {
            Errors.MERCHANT_NO_NOT_EXIST.throwException(MerchantException.class);
        }
        merchantResponse.setServices(getServices(no));
        return merchantResponse;
    }

    /**
     * 查询指定商户的服务清单
     *
     * @param no 商户号
     * @return 服务代码集合(数组)
     */
    @GetMapping("{no}/services")
    public List<String> getServices(@PathVariable String no) {
        return merchantService.getMerchantServices(no);
    }
}
