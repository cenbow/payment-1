/*
 * Project: Accounts
 * Document: StatusType
 * Date: 2020/8/27 16:29
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.beans;

/**
 * @author fengzl
 */
public enum StatusType {
    //成功
    SUCCESS("S"),
    //失败
    FAILURE("F"),
    //处理中
    PROCESSING("P");

    private String code;

    StatusType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
