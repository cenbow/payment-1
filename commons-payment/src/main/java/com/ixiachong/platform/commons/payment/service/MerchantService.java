/*
 * Project: Accounts
 * Document: MerchantService
 * Date: 2020/8/13 20:23
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.service;

import com.ixiachong.platform.commons.payment.dto.Merchant;
import com.ixiachong.platform.commons.payment.response.MerchantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author fengzl
 * @Date 2020/8/13
 */
@FeignClient("ms-payment-merchant")
public interface MerchantService {
    @GetMapping("/api/merchants/{no}")
    MerchantResponse getOne(@PathVariable String no);

    @GetMapping("/api/withdraws/{no}/channels/{channel}")
    Map<String, String> getChannel(@PathVariable String no, @PathVariable String channel);

    @PostMapping("/api/merchants")
    Merchant create(@RequestParam("customerNo") String customerNo, @RequestParam("accountNo") String accountNo,
                    @RequestParam("services") String[] services);

    @PostMapping("/api/withdraws/{no}/channels")
    Object createChannel(@PathVariable String no, @RequestParam Map<String, String> configs);

    @DeleteMapping("/api/withdraws/{no}/channels/{channel}")
    Object deleteChannel(@PathVariable String no, @PathVariable String channel);

    @GetMapping("/api/withdraws/{no}/channels")
    List<String> getChannel(@PathVariable String no);

    @GetMapping("/api/channels/{channel}/merchants/{no}")
    Map<String, String> getMerchantChannel(@PathVariable String channel, @PathVariable String no);

}
