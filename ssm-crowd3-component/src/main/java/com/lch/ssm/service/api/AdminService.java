package com.lch.ssm.service.api;

import com.lch.ssm.entity.Admin;

import java.util.List;

/**
 * @author lch
 * @create 2020-07-16 16:48
 */
public interface AdminService {

    int saveAdmin(Admin admin);

    List<Admin> getAll();
}
