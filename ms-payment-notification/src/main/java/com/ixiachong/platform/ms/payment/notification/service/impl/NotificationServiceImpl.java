/*
 * Project: Payment
 * Document: NotificationServiceImpl
 * Date: 2020/8/26 6:31 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.notification.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ixiachong.commons.beanutils.MapUtils;
import com.ixiachong.platform.commons.payment.notify.NotificationParams;
import com.ixiachong.platform.commons.payment.service.CryptoService;
import com.ixiachong.platform.ms.payment.notification.Const;
import com.ixiachong.platform.ms.payment.notification.model.Notification;
import com.ixiachong.platform.ms.payment.notification.model.NotificationLog;
import com.ixiachong.platform.ms.payment.notification.service.NotificationManagement;
import com.ixiachong.platform.ms.payment.notification.service.NotificationService;
import lombok.extern.java.Log;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Log
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private NotificationManagement management;
    private ObjectMapper objectMapper;
    private HttpClientConnectionManager httpClientConnectionManager;
    private CryptoService cryptoService;

    @Value("${application.notify.sign.enabled:true}")
    private boolean isSignEnabled;

    @Autowired
    public void setCryptoService(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Autowired
    public void setManagement(NotificationManagement management) {
        this.management = management;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    @Qualifier(Const.NOTIFICATION_HTTP_CLIENT)
    public void setHttpClientConnectionManager(HttpClientConnectionManager httpClientConnectionManager) {
        this.httpClientConnectionManager = httpClientConnectionManager;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notify(String notificationId) {
        log.info(String.format("开始执行通知[%s]", notificationId));

        Notification notification = management.getNotification(notificationId);
        if (notification == null) {
            log.warning(String.format("无效的通知[%s]", notificationId));
            return;
        } else if ("F".equals(notification.getState())) {
            log.warning(String.format("通知[%s]已结束", notificationId));
            return;
        }

        NotificationLog nlog = management.createNotificationLog(notificationId);

        CloseableHttpResponse response = null;
        try {
            NotificationParams params = objectMapper.readValue(notification.getParams(), NotificationParams.class);
            nlog.setRequestTime(new Date());
            HttpUriRequest request = RequestBuilder.post(params.getNotifyUrl())
                    .setEntity(buildEntity(params))
                    .build();

            response = obtainClient().execute(request);
            nlog.setResponseTime(new Date());

            String result = EntityUtils.toString(response.getEntity());
            nlog.setResult(result);
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
            nlog.setResult(ex.getMessage());
        } finally {
            HttpClientUtils.closeQuietly(response);
        }

        management.updateNotificationLog(nlog);
    }

    private HttpEntity buildEntity(NotificationParams params) throws UnsupportedEncodingException {
        String signType = Const.MD5;
        Map<String, String> data = MapUtils.map(
                Const.APP_ID, params.getAppId(),
                Const.SIGN_TYPE, signType,
                Const.CHARSET, "UTF-8",
                Const.TIMESTAMP, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),
                Const.CODE, params.getCode(),
                Const.MESSAGE, params.getMessage(),
                Const.ATTACH, params.getAttach(),
                Const.BIZ_CONTENT, params.getBizContent());

        if (isSignEnabled) {
            data.put("sign", cryptoService.sign(Const.SCENE_PAYMENT_GATEWAY, params.getAppId(), signType, data));
        }

        return new UrlEncodedFormEntity(data.entrySet().stream()
                .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()));
    }

    @Override
    public Notification startNotifyResult(String type, String identify, NotificationParams params) {
        // 创建通知基础信息
        Notification notification = management.getNotification(type, identify);
        if (notification != null) {
            log.warning(String.format("已存在通知[%s,%s]", type, identify));
            return notification;
        }

        notification = new Notification();
        try {
            notification.setParams(objectMapper.writeValueAsString(params));
        } catch (Exception ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
        }
        notification.setType(type);
        notification.setIdentify(identify);
        notification = management.createNotification(notification);

        notify(notification.getId());

        return notification;
    }

    private CloseableHttpClient obtainClient() {
        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setConnectionManagerShared(true)
                .build();
    }
}
