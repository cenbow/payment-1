/*
 * Project: Accounts
 * Document: ModelUtils
 * Date: 2020/8/3 2:19 下午
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.utils;

import com.ixiachong.commons.beanutils.BeanUtils;

/**
 * @author fengzl
 */
public class ModelUtils {
    public static <T, R> R toClient(T t, Class<R> clazz) {
        return BeanUtils.mapping(t, clazz);
    }

    public static <T, R> R formClient(T t, Class<R> clazz) {
        return toClient(t, clazz);
    }
}
