/*
 * Project: Payment
 * Document: HttpUtils
 * Date: 2020/7/20 5:02 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.gateway.generic.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.annotation.Nonnull;
import java.util.List;

public class HttpUtils {
    public static String getHeaderString(HttpHeaders headers, String key) {
        return getHeaderString(headers, key, null);
    }

    public static String getHeaderString(@Nonnull HttpHeaders headers, @Nonnull String key, String defaultValue) {
        if (headers.containsKey(key)) {
            List<String> values = headers.get(key);
            if (CollectionUtils.isNotEmpty(values)) {
                return String.join("", values);
            }
        }
        return defaultValue;
    }

    public static boolean getHeaderBoolean(HttpHeaders headers, String key) {
        return getHeaderBoolean(headers, key, Boolean.FALSE);
    }

    public static boolean getHeaderBoolean(HttpHeaders headers, String key, boolean defaultValue) {
        String value = getHeaderString(headers, key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public static boolean isCompatibleJSON(HttpHeaders headers) {
        return isCompatibleJSON(headers.getContentType());
    }

    public static boolean isCompatibleJSON(MediaType mediaType) {
        return mediaType != null && mediaType.isCompatibleWith(MediaType.APPLICATION_JSON);
    }
}
