/*
 * Project: Accounts
 * Document: Transaction
 * Date: 2020/8/10 11:19
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.service;

import com.ixiachong.platform.account.api.request.WithdrawConfirmRequest;
import com.ixiachong.platform.account.api.request.WithdrawFreezeRequest;
import com.ixiachong.platform.account.api.response.WithdrawConfirmResponse;
import com.ixiachong.platform.account.api.response.WithdrawFreezeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @Author fengzl
 * @Date 2020/8/10
 */
@FeignClient(value = "ms-account-transaction", path = "/api")
public interface TransactionService {
    @PostMapping(value = "/withdraw-freezes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    WithdrawFreezeResponse freezes(@RequestBody @Valid WithdrawFreezeRequest freezeRequest);

    @PostMapping("/withdraw-confirms")
    WithdrawConfirmResponse confirms(@RequestBody @Valid WithdrawConfirmRequest confirmRequest);
}
