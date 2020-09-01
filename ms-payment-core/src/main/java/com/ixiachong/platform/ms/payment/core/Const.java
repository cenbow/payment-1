/*
 * Project: Accounts
 * Document: Const
 * Date: 2020/7/29 14:27
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core;

import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.platform.account.api.beans.CustomerType;
import com.ixiachong.platform.account.api.beans.Gender;
import com.ixiachong.platform.commons.payment.beans.StatusType;

import java.util.Map;

/**
 * @Author fengzl
 * @Date 2020/7/29
 */
public interface Const extends com.ixiachong.platform.commons.payment.Const {
    Map<String, CustomerType> CUSTOMER_TYPES = MapUtils.map(String.class, CustomerType.class,
            "01", CustomerType.PERSONAL,
            "02", CustomerType.ENTERPRISE);

    Map<String, Gender> GENDERS = MapUtils.map(String.class, Gender.class,
            "F", Gender.FEMALE,
            "M", Gender.MALE);

    Map<String, StatusType> STATUS = MapUtils.map(String.class, StatusType.class, Const.SUCCESS_CODE,
            StatusType.SUCCESS, Const.FAILURE_CODE, StatusType.FAILURE, Const.PROCESSING_CODE, StatusType.PROCESSING);
    // ========================================================================
    // Trade Codes
    // ========================================================================
    String TRADE_WITHDRAW = "withdraw";

    // ========================================================================
    // State Codes
    // ========================================================================
    String AVAILABILITY = "A";

    // ========================================================================
    // WithdrawCode
    // ========================================================================
    String MERCHANT_NO = "merchantNo";
    String SUCCESS = "SUCCESS";
    String SUCCESS_CODE = "S";
    String PROCESSING_CODE = "P";
    String FAILURE_CODE = "F";
    String PROCESSING = "PROCESSING";
    String FAILURE = "FAILURE";
    String WXPAY_OPEN_ID = "open_id";
    String CHANNEL = "channel";
    String CHANNELS = "channels";
    String CHANNEL_WXPAY = "wxpay";
    String CHANNEL_ALIPAY = "alipay";

    // ========================================================================
    // Error Codes
    // ========================================================================
    String CUSTOMER_NOT_EXISTS = "CUSTOMER_NOT_EXISTS";
    String EC_TRADE_HANDLER_NOT_FOUND = "TRADE_HANDLER_NOT_FOUND";
    String WITHDRAW_NOT_ENOUGH_MIN = "WITHDRAW_NOT_ENOUGH_MIN";
    String WITHDRAW_FAILURE = "WITHDRAW_FAILURE";
    String NOT_ALLOW_WITHDRAW = "NOT_ALLOW_WITHDRAW";
    String MERCHANT_NOT_CONFIG = "MERCHANT_NOT_CONFIG";
    String WITHDRAW_CHANNEL_NOT_EXISTS = "WITHDRAW_CHANNEL_NOT_EXISTS";
    String REQUEST_BIZ_INVALID = "REQUEST_BIZ_INVALID";
    String REQUEST_BIZ_INCOMPLETE = "REQUEST_BIZ_INCOMPLETE";
    String WITHDRAW_APP_MERCHANT_NOT_EXISTS = "WITHDRAW_APP_MERCHANT_NOT_EXISTS";
    String MERCHANT_NOT_EXISTS = "MERCHANT_NOT_EXISTS";
    String UNKNOWN = "UNKNOWN";
    String REQUEST_ID_EXISTS_PARAMETER_ERROR = "REQUEST_ID_EXISTS_PARAMETER_ERROR";
    String ACCOUNT_UNUSABLE = "ACCOUNT_UNUSABLE";
    String PARAMETERS_ERROR = "PARAMETERS_ERROR";
    String CUSTOMER_TYPE_INVALID = "CUSTOMER_TYPE_INVALID";
    String WITHDRAW_NOT_PROCESSING = "WITHDRAW_NOT_PROCESSING";
    String OUT_TRADE_NO_EXISTS = "OUT_TRADE_NO_EXISTS";
}
