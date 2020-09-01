/*
 * Project: Payment
 * Document: ExpireTime
 * Date: 2020/8/27 3:27 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.util;

import lombok.Getter;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpireTime {
    @Getter
    private String expression;
    private int time;
    private int unit;
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("(\\d+)([dhmsDHMS])");

    public ExpireTime(String expression) {
        this.expression = expression;
        Matcher m = EXPRESSION_PATTERN.matcher(expression);
        if (m.find()) {
            this.time = Integer.parseInt(m.group(1));
            switch (m.group(2).toUpperCase()) {
                case "D":
                    this.unit = Calendar.DATE;
                    break;
                case "H":
                    this.unit = Calendar.HOUR;
                    break;
                case "M":
                    this.unit = Calendar.MINUTE;
                    break;
                case "S":
                    this.unit = Calendar.SECOND;
                    break;
                default:
                    throw new IllegalArgumentException(String.format("数据格式无效: %s", expression));
            }
        } else {
            throw new IllegalArgumentException(String.format("数据格式无效: %s", expression));
        }
    }

    public Date expire() {
        return expire(null);
    }

    public Date expire(Date start) {
        Calendar c = Calendar.getInstance();
        if (start != null) {
            c.setTime(start);
        }
        c.add(this.unit, time);
        return c.getTime();
    }
}