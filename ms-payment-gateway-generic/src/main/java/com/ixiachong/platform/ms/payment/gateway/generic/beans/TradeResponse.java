/*
 * Project: Payment
 * Document: TradeResponse
 * Date: 2020/7/20 8:22 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic.beans;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ixiachong.platform.ms.payment.gateway.generic.beans.json.RawValueSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder()
@AllArgsConstructor
@NoArgsConstructor
public class TradeResponse {
    private String appId;
    private String attach;
    private String format = "JSON";
    private String charset = "UTF-8";
    private String signType = "MD5";
    private String sign;
    @JsonSerialize(using = RawValueSerializer.class)
    private String bizContent;
    private String code;
    private String message;
    private String timestamp;
}
