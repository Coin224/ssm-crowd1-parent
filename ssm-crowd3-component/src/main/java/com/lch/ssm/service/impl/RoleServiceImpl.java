package com.lch.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lch.ssm.entity.Role;
import com.lch.ssm.entity.RoleExample;
import com.lch.ssm.mapper.RoleMapper;
import com.lch.ssm.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lch
 * @create 2020-07-22 0:32
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1.设置分页条件，开启分页
        PageHelper.startPage(pageNum,pageSize);

        // 2.执行查询
        List<Role> roles = roleMapper.selectRoleByKeyword(keyword);

        // 3.封装到pageInfo对象中
        return new PageInfo<>(roles);
    }

    @Override
    public void addRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void editRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void removeRole(List<Integer> id) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(id);

        roleMapper.deleteByExample(roleExample);
    }
}
