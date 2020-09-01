package com.ixiachong.platform.commons.payment.util;

/**
 * 消息摘要处理工具类
 *
 * @author wangbz
 */
public class DigestUtils {
    public static String md5(String data) {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(data);
    }

    public static String md5(byte[] data) {
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(data);
    }
}
