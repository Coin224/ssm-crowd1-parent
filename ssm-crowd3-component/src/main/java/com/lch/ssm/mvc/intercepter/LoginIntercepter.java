package com.lch.ssm.mvc.intercepter;

import com.lch.ssm.entity.Admin;
import com.lch.ssm.mvc.exception.myexception.AccessForbiddenException;
import com.lch.ssm.util.Constant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lch
 * @create 2020-07-18 18:49
 * 访问受保护资源时，用拦截器进行登录检查
 */
public class LoginIntercepter extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取session对象
        HttpSession session = request.getSession();
        // 2.从session中取出loginAdmin
        Admin loginAdmin = (Admin) session.getAttribute(Constant.LOGIN_ADMIN);

        // 3.检查loginAdmin是否为null
        if (loginAdmin == null) {
            // 4.如果为null抛出异常
            throw new AccessForbiddenException(Constant.ACCESS_FORBIDDEN);
        }

        // 5.如果已经登录返回true
        return true;
    }
}
