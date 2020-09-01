/*
 * Project: Payment
 * Document: MerchantService
 * Date: 2020/8/13 3:39 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.service;

import com.ixiachong.platform.ms.payment.merchant.exceptions.MerchantException;
import com.ixiachong.platform.ms.payment.merchant.model.Merchant;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface MerchantService {
    /**
     * 创建商户并开通服务
     *
     * @param customerNo 客户号
     * @param accountNo  账号
     * @param services   开通的服务集合
     * @return 创建成功后的商户
     */
    Merchant createMerchant(@NotEmpty String customerNo, @NotEmpty String accountNo, String... services) throws MerchantException;

    /**
     * 注销商户使商户不可用
     *
     * @param no 要注销的商户号
     */
    void disableMerchant(String no);

    /**
     * 获取商户信息
     *
     * @param no 商户号
     * @return 商户信息
     */
    Merchant getMerchant(String no);

    /**
     * 获取商户已经开通的服务清单
     *
     * @param no 商户号
     * @return 开通的服务清单
     */
    List<String> getMerchantServices(String no);
}
