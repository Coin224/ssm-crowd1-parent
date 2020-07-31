package com.lch.ssm.service.api;

import com.github.pagehelper.PageInfo;
import com.lch.ssm.entity.Admin;

import java.util.List;

/**
 * @author lch
 * @create 2020-07-16 16:48
 */
public interface AdminService {

    int saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin checkCount(String adminAccount, String password);

    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

    void remove(Integer id);

    int updateAdmin(Admin admin);

    Admin getAdminById(Integer id);

    Admin selectAdminByLoginAcct(String loginAcct);
}
