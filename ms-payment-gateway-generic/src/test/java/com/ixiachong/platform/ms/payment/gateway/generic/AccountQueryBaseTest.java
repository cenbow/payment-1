/*
 * Project: Payment
 * Document: AccountQueryTest
 * Date: 2020/8/24 6:17 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.UUID;

@RunWith(SpringRunner.class)
public class AccountQueryBaseTest extends GatewayBaseTest {

    @Test
    public void test() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(Const.REQUEST_ID, UUID.randomUUID().toString());
        formData.add(Const.APP_ID, "0123456789");
        formData.add(Const.METHOD, "xiac.trade.account.balance");
        formData.add(Const.TIMESTAMP, "0123456789");
        formData.add(Const.SIGN, "test11");
        formData.add(Const.BIZ_CONTENT, "{\"accountNo\":\"7DhI86M5\"}");

        Result<Object> result = client.post().uri("/v1/gateway")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Result.class)
                .returnResult().getResponseBody();

        System.out.println(result);
    }
}
