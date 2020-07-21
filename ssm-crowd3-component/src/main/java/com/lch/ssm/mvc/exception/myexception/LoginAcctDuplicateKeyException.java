package com.lch.ssm.mvc.exception.myexception;

/**
 * @author lch
 * @create 2020-07-21 15:44
 * 当loginAcct重复时抛出这个异常
 */
public class LoginAcctDuplicateKeyException extends RuntimeException{
    public LoginAcctDuplicateKeyException(String message) {
        super(message);
    }
}
