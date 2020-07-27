package com.lch.ssm.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.lch.ssm.entity.Admin;
import com.lch.ssm.service.api.AdminService;
import com.lch.ssm.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author lch
 * @create 2020-07-17 19:58
 */
@Controller()
public class AdminController {


    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/login")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                        @RequestParam("loginPass") String loginPass,
                        HttpSession session) {
        // 1.查出admin
        Admin admin = adminService.checkCount(loginAcct, loginPass);
        // 2.肯定不为空了
        session.setAttribute(Constant.LOGIN_ADMIN,admin);
        return "redirect:/admin/mainpage";
    }


    @RequestMapping("/admin/logout")
    public String doLogout(HttpSession session) {
        // 强制session失效
        session.invalidate();
        return "redirect:/admin/loginpage";
    }


    @RequestMapping("/admin/manage")
    public String adminManage(@RequestParam(value = "keyword" ,defaultValue = "") String keyword,
                                   @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                   Model modelMap) {
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute("pageInfo",pageInfo);
        return "admin-manage";
    }


    @RequestMapping("/admin/remove/{id}/{pageNum}/{keyword}.do")
    public String removeAdmin(@PathVariable("id") Integer id,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("keyword") String keyword,
                              HttpSession session) {
        Admin loginAdmin = (Admin) session.getAttribute(Constant.LOGIN_ADMIN);
        Integer adminId = loginAdmin.getId();
        System.out.println(id+"========="+adminId);
        if (id == adminId) {
            //如果id相同、抛出异常
            throw new RuntimeException("不准删自己！！！");
        }
        adminService.remove(id);
        return "redirect:/admin/manage?pageNum="+pageNum+"&keyword="+keyword;
    }
    @RequestMapping("/admin/addAdmin")
    public String saveAdmin(Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/manage?pageNum="+Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/to/update")
    public String updateAdmin(@RequestParam("id") Integer id,
                              @RequestParam("pageNum") Integer pageNum,
                              @RequestParam(value = "keyword",defaultValue = "") String keyword,
                              Model model) {
        Admin adminInUse = adminService.getAdminById(id);
        model.addAttribute(Constant.ADMIN_IN_USE,adminInUse);
        return "admin-update";
    }
    @RequestMapping("/admin/update")
    public String doUpdateAdmin(Admin admin,@RequestParam("pageNum") Integer pageNum,@RequestParam("keyword") String keyword) {
        adminService.updateAdmin(admin);
        return "redirect:/admin/manage?pageNum="+pageNum+"&keyword="+keyword;
    }

}
