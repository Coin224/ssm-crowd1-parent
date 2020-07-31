package com.lch.ssm.service.impl;

import com.lch.ssm.entity.Auth;
import com.lch.ssm.entity.AuthExample;
import com.lch.ssm.mapper.AuthMapper;
import com.lch.ssm.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * @author lch
 * @create 2020-07-30 22:44
 */

@Controller
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAllAuth() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAuthIdByRoleId(Integer roleId) {
        return authMapper.getAuthIdByRoleId(roleId);
    }

    @Override
    public void doSaveAuthForRole(Map<String, List<Integer>> map) {
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);
        // 删除原来的权限
        authMapper.deleteOldAuthByRoleId(roleId);
        // 保存新的权限
        List<Integer> authIdArray = map.get("authIdArray");
        if (authIdArray != null && authIdArray.size() > 0) {
            authMapper.doSaveAuthForRole(roleId,authIdArray);
        }
    }

    @Override
    public List<String> selectAuthNameByAdminId(Integer adminId) {
        return authMapper.selectAuthNameByAdminId(adminId);
    }
}
