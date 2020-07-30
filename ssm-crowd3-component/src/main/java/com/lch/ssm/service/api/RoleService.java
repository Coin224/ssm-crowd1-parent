package com.lch.ssm.service.api;

import com.github.pagehelper.PageInfo;
import com.lch.ssm.entity.Role;

import java.util.List;

/**
 * @author lch
 * @create 2020-07-22 0:30
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void addRole(Role role);

    void editRole(Role role);

    void removeRole(List<Integer> id);

    List<Role> findAssignedRoles(Integer adminId);

    List<Role> findUnAssignedRoles(Integer adminId);

    void saveRoleToAdmin(Integer adminId, List<Integer> roleIdList);
}
