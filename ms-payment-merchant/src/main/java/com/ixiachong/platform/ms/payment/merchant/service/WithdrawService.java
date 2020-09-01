/*
 * Project: Payment
 * Document: WithdrawService
 * Date: 2020/8/13 3:51 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.service;

import com.ixiachong.platform.ms.payment.merchant.exceptions.ConfigureException;

import java.util.List;
import java.util.Map;

public interface WithdrawService {
    /**
     * 创建渠道配置
     *
     * @param merchantNo 商户号
     * @param channel    渠道代码
     * @param configs    参数
     */
    void createChannel(String merchantNo, String channel, Map<String, String> configs) throws ConfigureException;

    /**
     * 删除渠道配置
     *
     * @param merchantNo
     * @param channel
     */
    void deleteChannel(String merchantNo, String channel);

    /**
     * 获取已配置的渠道代码
     *
     * @param merchantNo 商户号
     * @return 渠道代码集
     */
    List<String> getChannels(String merchantNo);

    /**
     * 获取渠道配置
     *
     * @param merchantNo 商户号
     * @param channel    渠道代码
     * @return 渠道配置
     */
    Map<String, String> getChannel(String merchantNo, String channel);
}
