/*
 * Project: Accounts
 * Document: MerchantService
 * Date: 2020/8/14 17:58
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.service;

import com.ixiachong.platform.commons.payment.dto.Merchant;
import com.ixiachong.platform.ms.payment.core.exceptions.MerchantException;
import com.ixiachong.platform.ms.payment.core.request.MerchantConfigChannelRequest;
import com.ixiachong.platform.ms.payment.core.request.MerchantConfigRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Author fengzl
 * @Date 2020/8/14
 */
public interface MerchantConfigService {
    Merchant create(@NotEmpty String accountNo, @NotEmpty String[] services) throws MerchantException;

    String configWithdraw(@NotEmpty String merchantNo, @NotEmpty Map<String, Map<String, String>> map) throws MerchantException;

    String configWithdraw(@NotNull MerchantConfigRequest request) throws MerchantException;

    String configWithdraw(@NotNull MerchantConfigChannelRequest request) throws MerchantException;

    String delConfigWithdraw(@NotEmpty String merchantNo, @NotEmpty String channel);

    List<String> getConfigWithdraw(@NotEmpty String merchantNo);
}
