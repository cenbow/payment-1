/*
 * Project: Payment
 * Document: MerchantManager
 * Date: 2020/8/18 5:07 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.alipay.domain;

import com.ixiachong.platform.commons.payment.util.HttpUtils;
import lombok.Builder;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.logging.Level;

@Log
@Builder
public class MerchantManager {

    private static final String DEFAULT_FILE_PREFIX = "MMF";
    private static final String DEFAULT_FILE_SUFFIX = ".tmp";
    private Function<String, Map<String, String>> configFinder;
    private File directory;
    private String prefix;
    private final ConcurrentHashMap<String, File> fileCache = new ConcurrentHashMap<String, File>();
    private final Map<String, Merchant> merchantConfigOfNo = new ConcurrentHashMap<>();

    public Merchant get(String no) {
        return merchantConfigOfNo.computeIfAbsent(no, merchantNo -> new Merchant(
                no, configFinder.apply(merchantNo), this::getLocalPath, this::readContent));
    }

    public void release(String no) {
        merchantConfigOfNo.remove(no);
    }

    private String readContent(String uri) {
        File file = fileCache.computeIfAbsent(uri, this::download);
        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            log.log(Level.WARNING, ex.getMessage(), ex);
            return null;
        }
    }

    private String getLocalPath(String uri) {
        File file = fileCache.computeIfAbsent(uri, this::download);
        return file.getAbsolutePath();
    }

    private File download(String uri) {
        URI uriObject;
        try {
            uriObject = new URI(uri);
        } catch (URISyntaxException e) {
            log.severe(e.getMessage());
            return null;
        }
        if ("file".equals(uriObject.getScheme())) {
            return new File(uri);
        } else {
            try {
                File file = File.createTempFile(
                        Optional.ofNullable(this.prefix).orElse(DEFAULT_FILE_PREFIX),
                        Optional.ofNullable(HttpUtils.getSuffix(uri)).orElse(DEFAULT_FILE_SUFFIX),
                        directory);

                FileUtils.copyURLToFile(uriObject.toURL(), file);

                return file;
            } catch (Exception ex) {
                log.severe(ex.getMessage());
                return null;
            }
        }
    }
}
