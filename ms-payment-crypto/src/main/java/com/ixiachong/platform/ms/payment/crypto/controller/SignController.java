/*
 * Project: Payment
 * Document: SignController
 * Date: 2020/7/23 7:35 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.crypto.controller;

import com.ixiachong.platform.ms.payment.crypto.service.SignService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Level;

@Log
@RestController
@RequestMapping("/sign")
public class SignController {
    private SignService service;

    @Autowired
    public void setService(SignService service) {
        this.service = service;
    }

    @PostMapping("verify")
    public boolean verify(@RequestParam("type") String type,
                          @RequestParam("identity") String identity,
                          @RequestParam("algorithm") String algorithm,
                          @RequestParam("sign") String sign,
                          @RequestBody Map<String, String> data) {
        try {
            return service.verify(type, identity, algorithm, sign, data);
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
            return false;
        }
    }

    @PostMapping("sign")
    public String sign(@RequestParam("type") String type,
                       @RequestParam("identity") String identity,
                       @RequestParam("algorithm") String algorithm,
                       @RequestBody Map<String, String> data) {
        try {
            return service.sign(type, identity, algorithm, data);
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
            return null;
        }
    }
}
