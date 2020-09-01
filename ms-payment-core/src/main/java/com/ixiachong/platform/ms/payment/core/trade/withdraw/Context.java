/*
 * Project: Payment
 * Document: Context
 * Date: 2020/8/7 2:18 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.withdraw;

import com.ixiachong.commons.etc.container.BeanFinder;
import com.ixiachong.platform.commons.payment.dto.Merchant;
import com.ixiachong.platform.commons.payment.service.MerchantService;
import com.ixiachong.platform.commons.payment.service.TransactionService;
import com.ixiachong.platform.ms.payment.core.model.tradings.*;
import com.ixiachong.platform.ms.payment.core.model.trans.Withdraw;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Builder
public class Context {
    private BeanFinder beanFinder;
    private TransactionService transactionService;
    private MerchantService merchantService;
    private Supplier<String> idGenerator;
    private Supplier<String> noGenerator;
    private Function<Withdraw, Withdraw> saveWithdraw;
    private Consumer<WithdrawFreezesRequest> saveWithdrawFreezeRequest;
    private Consumer<WithdrawAffirmRequest> saveWithdrawConfirmRequest;
    private Consumer<WithdrawFreezesResponse> saveWithdrawFreezeResponse;
    private Consumer<WithdrawAffirmResponse> saveWithdrawConfirmResponse;
    private Consumer<WithdrawChannelRequest> saveWithdrawChannelRequest;
    private Consumer<WithdrawChannelResponse> saveWithdrawChannelResponse;
    private Function<String, Optional<Withdraw>> tradeFinder;
    private Function<String, Boolean> allowWithdraw;
    private BiFunction<String, String, String> applicationMerchant;
    private Function<String, Merchant> merchantFinder;
    private BiFunction<String, String, Map<String, String>> merchantChannelParameters;
}
