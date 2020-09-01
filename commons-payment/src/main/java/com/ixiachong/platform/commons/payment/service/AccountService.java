/*
 * Project: Accounts
 * Document: AccountService
 * Date: 2020/7/29 14:36
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.service;

import com.ixiachong.platform.account.api.dto.FlowsDto;
import com.ixiachong.platform.account.api.request.CustomerRegisterRequest;
import com.ixiachong.platform.account.api.vo.AccountSubjectBalanceVo;
import com.ixiachong.platform.account.api.vo.AccountVo;
import com.ixiachong.platform.account.api.vo.CustomerRegisterVo;
import com.ixiachong.platform.account.api.vo.FlowsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author fengzl
 * @Date 2020/7/29
 */
@FeignClient(value = "ms-account-core", path = "/api")
public interface AccountService {
    /**
     * 客户注册
     *
     * @param customerRegister 客户注册信息
     * @return 注册响应
     */
    @PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CustomerRegisterVo registerCustomer(@RequestBody CustomerRegisterRequest customerRegister);

    /**
     * 客户开户
     *
     * @param no 客户id
     * @return 账户信息
     */
    @PostMapping(value = "/customers/{no}/accounts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AccountVo createAccounts(@PathVariable(value = "no") String no);

    /**
     * 获取客户的账户信息
     *
     * @param no
     * @return 客户账户信息
     */
    @GetMapping(value = "/customers/{no}/accounts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<AccountVo> getAccounts(@PathVariable(value = "no") String no);

    /**
     * 获取账户余额信息
     *
     * @param no 账户号
     * @return 账户余额信息
     */
    @GetMapping(value = "/accounts/{no}/balances", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<AccountSubjectBalanceVo> balance(@PathVariable String no);

    /**
     * 获取账户摘要信息
     *
     * @param no 账户号
     * @return 账户摘要信息
     */
    @GetMapping(value = "/accounts/{no}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    AccountVo getAccountSummary(@PathVariable String no);

    /**
     * 入账
     *
     * @param flowsDto 入账信息
     * @return 入账响应
     */
    @PostMapping(value = "/accounts/inflows", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    FlowsVo inflows(@RequestBody @Valid FlowsDto flowsDto);

    /**
     * 消费
     *
     * @param flowsDto 消费信息
     * @return 消费响应
     */
    @PostMapping(value = "/accounts/outflows", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    FlowsVo consumes(@RequestBody @Valid FlowsDto flowsDto);

    /**
     * @param accountNo  账号
     * @param customerNo 客户编号
     * @return 是否合法
     */
    @PostMapping(value = "/accounts/verification", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    boolean verification(@RequestParam("accountNo") String accountNo, @RequestParam("customerNo") String customerNo);
}
