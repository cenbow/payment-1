/**
 * Project: account-api
 * Document: EnterprisePayment
 * Date: 2020/8/7 09:52
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.commons.payment.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: wangcy
 */
@Data
public class EnterprisePayment {

    /**商户订单号*/
    private String outTradeNo;

    /**金额*/
    private BigDecimal amount;

    /**商户号*/
    private String merchantNo;

    /**平台单号*/
    private String tradeNo;

    /**收款用户id*/
    private String payeeId;

    /**付款摘要*/
    private String summary;

    /**付款备注*/
    private String body;

    /**订单支付状态*/
    private String state;

    /**付款成功时间*/
    private Date paymentTime;

}
