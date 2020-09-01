/*
 * Project: Payment
 * Document: CryptoService
 * Date: 2020/7/23 8:18 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("ms-payment-crypto")
public interface CryptoService {
    /**
     * 验证签名结果
     *
     * @param type      签名类型/场景
     * @param identity  签名身份主体
     * @param algorithm 算法
     * @param sign      要验证的签名值
     * @param data      签名数据
     * @return 验证结果
     */
    @PostMapping(value = "/sign/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    boolean verifySign(
            @RequestParam("type") String type,
            @RequestParam("identity") String identity,
            @RequestParam("algorithm") String algorithm,
            @RequestParam("sign") String sign,
            @RequestBody Map<String, String> data);

    /**
     * 计算签名
     *
     * @param type      签名类型/场景
     * @param identity  签名身份主体
     * @param algorithm 算法
     * @param data      签名数据
     * @return 签名结果
     */
    @PostMapping(value = "/sign/sign", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String sign(
            @RequestParam("type") String type,
            @RequestParam("identity") String identity,
            @RequestParam("algorithm") String algorithm,
            @RequestBody Map<String, String> data);
}
