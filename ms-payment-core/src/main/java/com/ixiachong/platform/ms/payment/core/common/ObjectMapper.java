/*
 * Project: Payment
 * Document: ObjectMapper
 * Date: 2020/8/19 5:03 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.common;

public interface ObjectMapper {
    <T> T readObject(String content, Class<T> clazz);
}
