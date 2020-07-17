package com.lch.ssm.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lch
 * @create 2020-07-17 13:53
 *
 * 判断请求是普通请求还是ajax请求
 * true：ajax请求
 * false: 非ajax请求
 */
public class ReqTypeUtil {

    public static boolean judgeReqType(HttpServletRequest request) {
        String acceptHeader = request.getHeader("accept");
        String XRequestdHeader = request.getHeader("X-Requested-With");
        return ((acceptHeader != null && acceptHeader.contains("application/json"))
                ||
                (XRequestdHeader != null && "XMLHttpRequest".equals(XRequestdHeader)));
    }
}
