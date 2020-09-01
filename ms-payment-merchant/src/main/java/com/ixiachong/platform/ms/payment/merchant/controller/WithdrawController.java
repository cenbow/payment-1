/*
 * Project: Payment
 * Document: WithdrawController
 * Date: 2020/8/12 5:51 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.controller;

import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.commons.etc.exceptions.BaseException;
import com.ixiachong.platform.ms.payment.merchant.Const;
import com.ixiachong.platform.ms.payment.merchant.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/withdraws")
public class WithdrawController {
    private WithdrawService service;

    @Autowired
    public void setService(WithdrawService service) {
        this.service = service;
    }

    @PostMapping
    public Object create() {
        return null;
    }

    /**
     * 获取商户的提现参数配置
     *
     * @param no 商户号
     * @return 创建结果
     */
    @GetMapping("{no}")
    public Object getOne(@PathVariable String no) {
        return null;
    }

    /**
     * 创建渠道参数
     *
     * @param no 商户号
     * @return 创建结果
     */
    @PostMapping("{no}/channels")
    public Object createChannel(@PathVariable String no, @RequestParam Map<String, String> configs) throws BaseException {
        String channel = configs.get(Const.CONFIGURATION_CHANNEL);
        service.createChannel(no, channel, configs.entrySet().stream()
                .filter(entry -> !Const.CONFIGURATION_CHANNEL.equals(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        return MapUtils.map(Const.MERCHANT_NO, no, Const.CONFIGURATION_CHANNEL, channel);
    }

    /**
     * 删除渠道配置
     *
     * @param no      商户号
     * @param channel 渠道代码
     * @return 删除结果
     */
    @DeleteMapping("{no}/channels/{channel}")
    public Object deleteChannel(@PathVariable String no, @PathVariable String channel) {
        service.deleteChannel(no, channel);
        return MapUtils.map(Const.MERCHANT_NO, no, Const.CONFIGURATION_CHANNEL, channel);
    }

    /**
     * 查询渠道配置
     *
     * @param no      商户号
     * @param channel 渠道代码
     * @return 查询结果
     */
    @GetMapping("{no}/channels/{channel}")
    public Map<String, String> getChannel(@PathVariable String no, @PathVariable String channel) {
        return service.getChannel(no, channel);
    }

    /**
     * 获取商户已配置的渠道代码集合
     *
     * @param no      商户号
     * @return 渠道代码集合
     */
    @GetMapping("{no}/channels")
    public List<String> getChannel(@PathVariable String no) {
        return service.getChannels(no);
    }
}
