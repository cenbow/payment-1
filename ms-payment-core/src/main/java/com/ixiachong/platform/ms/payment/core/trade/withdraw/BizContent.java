/*
 * Project: Payment
 * Document: BizContent
 * Date: 2020/8/8 11:06 上午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.core.trade.withdraw;

import com.ixiachong.platform.ms.payment.core.trade.GeneralAmountBizContent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizContent extends GeneralAmountBizContent {

    private String channel;

}
