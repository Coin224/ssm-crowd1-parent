package com.lch.ssm.mapper;

import com.lch.ssm.entity.Auth;
import com.lch.ssm.entity.AuthExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Integer> getAuthIdByRoleId(Integer roleId);

    void deleteOldAuthByRoleId(Integer roleId);

    void doSaveAuthForRole(@Param("roleId") Integer roleId, @Param("authIdArray") List<Integer> authIdArray);
}