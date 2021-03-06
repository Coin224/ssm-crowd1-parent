package com.lch.ssm.mapper;

import com.lch.ssm.entity.Role;
import com.lch.ssm.entity.RoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleByKeyword(String keyword);

    List<Role> findAssignedRoles(Integer adminId);

    List<Role> findUnAssignedRoles(Integer adminId);

    void deleteOldRoleByAdminId(Integer adminId);

    void saveNewRoleToAdmin(@Param("adminId") Integer adminId, @Param("roleIdList") List<Integer> roleIdList);
}