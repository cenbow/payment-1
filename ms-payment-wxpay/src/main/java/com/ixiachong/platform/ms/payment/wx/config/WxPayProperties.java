/**
 * Project: parent
 * Document: TransfersServiceImpl
 * Date: 2020/8/4 14:41
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "wxpay")
public class WxPayProperties {
  /**
   * 设置微信公众号或者小程序等的appid
   */
  private String appId;

  /**
   * 微信支付商户号
   */
  private String mchId;

  /**
   * 微信支付商户密钥
   */
  private String mchKey;

  /**
   * apiclient_cert.p12文件的绝对路径
   */
  private String keyPath;


  private String certFilePath;

}
