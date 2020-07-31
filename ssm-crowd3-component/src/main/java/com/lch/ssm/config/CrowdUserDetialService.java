package com.lch.ssm.config;

import com.lch.ssm.entity.Admin;
import com.lch.ssm.entity.Role;
import com.lch.ssm.service.api.AdminService;
import com.lch.ssm.service.api.AuthService;
import com.lch.ssm.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lch
 * @create 2020-07-31 19:59
 */
@Component
public class CrowdUserDetialService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据username查询到admin对象
        Admin admin = adminService.selectAdminByLoginAcct(username);

        System.out.println("admin的账号："+admin.getUserName());

        // 获取adminId
        Integer adminId = admin.getId();

        // 根据adminId查询该admin的role
        List<Role> assignedRoleList = roleService.findAssignedRoles(adminId);

        // 根据adminId查询该Admin的auth的名字
        List<String> authNameList = authService.selectAuthNameByAdminId(adminId);

        List<GrantedAuthority> authorities = new ArrayList<>();

        // 遍历assignedRoleList 得到
        for (Role role: assignedRoleList){
            String roleName = role.getName();
            roleName = "ROLE_" + roleName;

            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }

        // 遍历authNameList
        for (String authName:authNameList) {

            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }

        SecurityAdmin securityAdmin = new SecurityAdmin(admin,authorities);
        return securityAdmin;
    }
}
