/*
 * Project: Payment
 * Document: Request
 * Date: 2020/8/19 6:33 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.customer;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Request {
    private String type;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String mobile;
    private String idNumber;
    private Date birthday;
    private String gender;
    private String businessLicenseNo;
    private String legalIdNumber;
    private String legalName;
    private BigDecimal registeredCapital;
    private String scope;
}
