package com.ixiachong.platform.commons.payment.util;

import org.apache.commons.codec.binary.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 签名工具箱
 *
 * @author wangbz
 */
public class SignUtils {
    private static final String SIGN = "sign";
    private static final String KEY = "app_secret";

    /**
     * 规范化消息文本(附加上key信息)
     *
     * @param text
     * @param key
     * @return
     */
    private static String normalizeMessage(String text, String key) {
        return text + "&" + KEY + "=" + key;
    }

    /**
     * 规范化消息文本(附加上key信息)
     *
     * @param data
     * @param key
     * @return
     */
    private static String normalizeMessage(Map<String, String> data, String key) {
        StringBuilder sb = new StringBuilder();
        data.keySet().stream()
                .filter(name -> !SIGN.equals(name))
                .sorted()
                .forEach(name -> sb.append(name).append("=").append(data.get(name)).append("&"));
        return sb.append(KEY).append("=").append(key).toString();
    }

    /**
     * 签名字符串
     *
     * @param text    原始文本
     * @param key     密钥
     * @param charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String charset) {
        String plain = normalizeMessage(text, key);
        String digest = DigestUtils.md5(StringUtils.getBytesUnchecked(plain, charset));
        return digest != null ? digest.toUpperCase() : null;
    }

    /**
     * 签名MAP数据
     *
     * @param data    数据
     * @param key     签名密钥
     * @param charset 编码格式
     * @return 签名结果
     */
    public static String sign(Map<String, String> data, String key, String charset) {
        String plain = normalizeMessage(data, key);
        String digest = DigestUtils.md5(StringUtils.getBytesUnchecked(plain, charset));
        return digest != null ? digest.toUpperCase() : null;
    }

    /**
     * 签名字符串
     *
     * @param text    原始文本
     * @param sign    签名结果
     * @param key     密钥
     * @param charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String charset) {
        String digest = sign(text, key, charset);
        return digest.equals(sign);
    }

    public static Map<String, String> convert(Map<String, ?> source) {
        HashMap<String, String> m = new HashMap<>();
        source.forEach((key, val) -> {
            if (val == null) {
//                log.warning(String.format("%s is null", key));
            } else {
                m.put(key, val.toString());
            }
        });
        return m;
    }

}