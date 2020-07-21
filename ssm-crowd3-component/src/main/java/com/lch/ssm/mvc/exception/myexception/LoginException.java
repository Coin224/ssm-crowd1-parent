package com.lch.ssm.mvc.exception.myexception;

/**
 * @author lch
 * @create 2020-07-17 19:39
 * 自定义登录异常
 */
public class LoginException extends RuntimeException{

    private static final long serialVersionUID = 9L;
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

}
