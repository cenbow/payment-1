/**
 * Project: parent
 * Document: TransfersServiceImpl
 * Date: 2020/8/4 14:41
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@Component
public class AliPayProperties {


  public String gateway;
  /**
   * 设置支付宝的appid
   */
  public String appId;
  /**
   * 支付宝私钥
   */
  public String rsaPrivateKey;
  /**
   * 支付宝公钥
   */
  public String alipayPublicKey;

  public String charset;

  public String format;

  public String signType;

  public String appCertPath;

  public String alipayCertPath;

  public String alipayRootCertPath;

  public String certFilePath;

}
