/**
 * Project: parent
 * Document: ResponseErrorCode
 * Date: 2020/8/24 18:04
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.response;

import lombok.Data;

/**
 * Author: wangcy
 */
@Data
public class ResponseErrorCode<T> {

    private String errorCode;

    private String errorMessage;

    private T response;


}
