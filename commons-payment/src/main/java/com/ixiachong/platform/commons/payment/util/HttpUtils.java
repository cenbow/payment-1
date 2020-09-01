/*
 * Project: Payment
 * Document: HttpUtils
 * Date: 2020/8/28 4:52 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {
    private static final Pattern SUFFIX_PATTERN = Pattern.compile("[^\\.]+(\\.\\w+)$|[^\\.]+(\\.\\w+)\\?");

    public static String getSuffix(String url) {
        Matcher matcher = SUFFIX_PATTERN.matcher(url);
        if (matcher.find()) {
            return Optional.ofNullable(matcher.group(1)).orElse(matcher.group(2));
        } else {
            return null;
        }
    }
}
