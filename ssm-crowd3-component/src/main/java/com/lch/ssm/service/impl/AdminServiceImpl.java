package com.lch.ssm.service.impl;

import com.lch.ssm.entity.Admin;
import com.lch.ssm.entity.AdminExample;
import com.lch.ssm.mapper.AdminMapper;
import com.lch.ssm.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lch
 * @create 2020-07-16 16:49
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public int saveAdmin(Admin admin) {
        return adminMapper.insert(admin);
    }

    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }
}
