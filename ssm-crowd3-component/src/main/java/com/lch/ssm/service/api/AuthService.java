package com.lch.ssm.service.api;

import com.lch.ssm.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @author lch
 * @create 2020-07-30 22:43
 */
public interface AuthService {
    List<Auth> getAllAuth();

    List<Integer> getAuthIdByRoleId(Integer roleId);

    void doSaveAuthForRole(Map<String, List<Integer>> map);

    List<String> selectAuthNameByAdminId(Integer adminId);
}
