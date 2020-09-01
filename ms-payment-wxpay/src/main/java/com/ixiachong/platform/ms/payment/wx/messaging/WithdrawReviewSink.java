/*
 * Project: Accounts
 * Document: WithdrawReview
 * Date: 2020/8/25 17:37
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.wx.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @Author fengzl
 * @Date 2020/8/25
 * <p>
 * 提现记录审查输入源
 */
public interface WithdrawReviewSink {
    String WITHDRAW_REVIEW = "withdraw-review-sink";

    @Input(WITHDRAW_REVIEW)
    SubscribableChannel inputReview();
}
