package com.lch.ssm.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author lch
 * @create 2020-07-17 13:53
 */
public class CrowdUtil {

    /**
     * 判断请求是普通请求还是ajax请求
     * true：ajax请求
     * false: 非ajax请求
     */
    public static boolean judgeReqType(HttpServletRequest request) {
        String acceptHeader = request.getHeader("accept");
        String XRequestdHeader = request.getHeader("X-Requested-With");
        return ((acceptHeader != null && acceptHeader.contains("application/json"))
                ||
                (XRequestdHeader != null && "XMLHttpRequest".equals(XRequestdHeader)));
    }


    /**
     * MD5加密
     */
    public static String MD5(String source) {
        // 1.判断字符串是否有效
        if (source  ==  null || source.length() == 0) {
            // 2.如果无效抛出异常
            throw new RuntimeException(Constant.MESSAGE_STR_INVALIDATE);
        }
        try {
            // 3.获取MessageDigest对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 4.加密
            byte[] output = messageDigest.digest(source.getBytes());
            // 5.转码、创建bigInteger对象
            int signnum = 1;//正数
            BigInteger bigInteger = new BigInteger(signnum, output);
            // 6.按照16进制将bigInteger转换为字符串
            int radix = 16;
            String result = bigInteger.toString(radix).toUpperCase();
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
