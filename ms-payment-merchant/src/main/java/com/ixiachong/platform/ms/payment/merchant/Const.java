/*
 * Project: Accounts
 * Document: Const
 * Date: 2020/8/13 15:51
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant;

/**
 * @Author fengzl
 * @Date 2020/8/13
 */
public interface Const {
    // ------------------------------------------------------------------------
    //  etc
    // ------------------------------------------------------------------------
    String MERCHANT_NO = "merchantNo";
    // ------------------------------------------------------------------------
    //  Service codes
    // ------------------------------------------------------------------------
    String SERVICE_WITHDRAW = "withdraw";
    // ------------------------------------------------------------------------
    //  Configuration properties
    // ------------------------------------------------------------------------
    String CONFIGURATION_CHANNEL = "channel";
    String CONFIGURATION_SERVICE = "service";
    // ------------------------------------------------------------------------
    //  Error Codes
    // ------------------------------------------------------------------------
    String MERCHANT_NO_EXISTS = "MERCHANT_NO_EXISTS";
    String MERCHANT_NO_NOT_EXIST = "MERCHANT_NO_NOT_EXIST";
    String SERVICE_NOT_OPEN = "SERVICE_NOT_OPEN";
    String WITHDRAW_CHANNEL_EXIST = "WITHDRAW_CHANNEL_EXIST";
    String CUSTOMER_ACCOUNT_ERROR = "MERCHANT_ACCOUNT_ERROR";
}
