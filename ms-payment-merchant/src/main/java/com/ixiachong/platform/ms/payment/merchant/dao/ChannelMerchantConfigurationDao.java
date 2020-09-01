/**
 * Project: parent
 * Document: ChannelMerchantConfigurationDao
 * Date: 2020/8/19 16:32
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.dao;

import com.ixiachong.platform.ms.payment.merchant.model.ChannelMerchantConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author: wangcy
 */
public interface ChannelMerchantConfigurationDao extends JpaRepository<ChannelMerchantConfiguration, String> {

    List<ChannelMerchantConfiguration> findByOwnerIdAndClassify(String merchantNo,String channel);

}
