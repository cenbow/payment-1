/*
 * Project: Payment
 * Document: JSONTest
 * Date: 2020/7/21 5:02 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ixiachong.platform.ms.payment.gateway.generic.beans.json.RawValueSerializer;
import com.ixiachong.platform.ms.payment.gateway.generic.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.io.IOException;

public class JSONTest {
    @Test
    public void test() throws IOException {

        Response response = Response.builder()
                .appId("app1001")
                .timestamp(DateUtils.currentTimestamp())
                .bizContent("[\"123\"]")
                .build();

        System.out.println(new ObjectMapper().writeValueAsString(response));


//        JsonFactory factory = JsonFactory.builder()
//                .build();
//
//
//        JsonGenerator jg = factory.createGenerator(System.out);
//
//        jg.setCodec(new ObjectMapper());
//
//
//        TradeResponse response = TradeResponse.builder()
//                .appId("123123123")
//                .attach("reutn-fff")
//                .build();
//
//        jg.writeStartObject();
//        jg.writeFieldName("test");
//        jg.writeObject(response);
////        jg.writeStringField("name","wangbz");
////        jg.writeStringField("sex","M");
//        jg.writeFieldName("biz_content");
//        jg.writeRawValue("[\"12312\",\"456\"]");
//        jg.writeEndObject();

//        jg.flush();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String appId;
        private String timestamp;
        @JsonSerialize(using = RawValueSerializer.class)
        private String bizContent;
    }
}
