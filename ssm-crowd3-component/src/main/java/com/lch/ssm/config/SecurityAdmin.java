package com.lch.ssm.config;

import com.lch.ssm.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * @author lch
 * @create 2020-07-31 17:59
 */
public class SecurityAdmin extends User {

    private static final long serialVersionUID = 1L;

    // 原始的admin对象 包含admin对象的全部属性
    private Admin originalAdmin;

    // 传入原始的admin对象和权限
    public SecurityAdmin(Admin originalAdmin , List<GrantedAuthority> authorities) {
        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);

        // 把原始的admin对象赋值给originalAdmin属性
        this.originalAdmin = originalAdmin;
    }

    public Admin getOrignalAdmin() {
        return originalAdmin;
    }
}
