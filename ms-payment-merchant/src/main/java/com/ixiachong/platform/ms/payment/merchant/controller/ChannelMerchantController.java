/**
 * Project: parent
 * Document: ChannelMerchantController
 * Date: 2020/8/19 16:33
 * Author: wangcy
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.controller;

import com.ixiachong.platform.ms.payment.merchant.service.ChannelMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Author: wangcy
 */
@RestController
@RequestMapping("/api/channels")
public class ChannelMerchantController {

    private ChannelMerchantService service;

    @Autowired
    public void setService(ChannelMerchantService service) {
        this.service = service;
    }

    @GetMapping("{channel}/merchants/{no}")
    public Map<String, String> getChannel(@PathVariable String no, @PathVariable String channel) {
        return service.getChannel(no, channel);
    }

    @PostMapping("{channel}/merchants")
    public Object saveChannelMerchantsConfig(@PathVariable String channel, @RequestParam Map<String,String> merchantsConfig ) {

        return service.createChannelMerchantsConfig(channel,merchantsConfig);
    }


}
