package com.lch.ssm.mvc.exception.myexception;

/**
 * @author lch
 * @create 2020-07-18 18:56
 * 禁止非登录状态访问非公共资源
 */
public class AccessForbiddenException extends RuntimeException{
    public AccessForbiddenException(String message) {
        super(message);
    }
}
