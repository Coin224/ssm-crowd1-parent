package com.lch.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lch.ssm.entity.Admin;
import com.lch.ssm.entity.AdminExample;
import com.lch.ssm.mapper.AdminMapper;
import com.lch.ssm.mvc.exception.myexception.LoginAcctDuplicateKeyException;
import com.lch.ssm.mvc.exception.myexception.LoginException;
import com.lch.ssm.service.api.AdminService;
import com.lch.ssm.util.Constant;
import com.lch.ssm.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author lch
 * @create 2020-07-16 16:49
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;





    @Override
    public int updateAdmin(Admin admin) {

        try {
            return adminMapper.updateByPrimaryKeySelective(admin);
        }catch (Exception e) {
            // 当这个异常属于DuplicateKeyException时抛出以下异常
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctDuplicateKeyException(Constant.LOGINACCT_DUPLICATE);
            }
        }
        return 0;
    }

    @Override
    public Admin getAdminById(Integer id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    public int saveAdmin(Admin admin) {

        // 1.密码加密
        String userPswd = admin.getUserPswd();
        userPswd = CrowdUtil.MD5(userPswd);
        admin.setUserPswd(userPswd);

        // 2.创建时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);
        int count = 0;
        try {
            count = adminMapper.insert(admin);
        }catch (Exception e) {
            // 当这个异常属于DuplicateKeyException时抛出以下异常
           if (e instanceof DuplicateKeyException) {
               throw new LoginAcctDuplicateKeyException(Constant.LOGINACCT_DUPLICATE);
           }
        }
        return count;
    }

    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    public Admin checkCount(String loginAcct, String loginPass) {
        // 1.先创建AdminExample对象、构建查询条件
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);

        // 2.从数据库中查
        List<Admin> adminList = adminMapper.selectByExample(adminExample);

        // 3.判断
        if (adminList == null || adminList.size() ==0) {
            throw new LoginException();
        }
        if (adminList.size() >1) {
            throw new LoginException();
        }
        // 4.获取admin
        Admin admin = adminList.get(0);

        // 5.判断admin是否为空
        if (admin == null) {
            throw new LoginException();
        }
        // 6.比较数据库中的密码和明文密码加密后的密码是否一致
        String userPswd = admin.getUserPswd();
        String md5Pass = CrowdUtil.MD5(loginPass);
        if (!Objects.equals(userPswd,md5Pass)) {
            // 7.如果不相等异常处理
            throw new LoginException();
        }
        // 8.如果一致返回对象
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 2.设置分页条件,开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 1.查询
        List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);

        // 3.封装到pageInfo对象中
        return new PageInfo<>(adminList);
    }

    @Override
    public void remove(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }



}
