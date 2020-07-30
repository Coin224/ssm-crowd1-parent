package com.lch.ssm.mvc.controller;

import com.lch.ssm.entity.Auth;
import com.lch.ssm.entity.Role;
import com.lch.ssm.service.api.AuthService;
import com.lch.ssm.service.api.RoleService;
import com.lch.ssm.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author lch
 * @create 2020-07-30 20:19
 */
@Controller
public class AssignRoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;


    @RequestMapping("/assign/role/page")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, Model model) {
        //查询已分配的角色
        List<Role> assignedRoles =  roleService.findAssignedRoles(adminId);
        // 查询未分配的角色
        List<Role> unAssignedRoles = roleService.findUnAssignedRoles(adminId);
        // 把已分配的角色存放到model
        model.addAttribute("assignedRoles",assignedRoles);
        // 把未分配的角色存放到model
        model.addAttribute("unAssignedRoles",unAssignedRoles);
        return "assign-role-page";
    }

    @RequestMapping("/assign/do/save/role")
    public String doSaveAdminRole(@RequestParam("adminId") Integer adminId,
                                   @RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("keyword") String keyword,
                                   // roleIdList不是必须的
                                   @RequestParam(value = "roleIdList",required = false) List<Integer> roleIdList) {

        // 保存amdin的角色
        roleService.saveRoleToAdmin(adminId,roleIdList);
        return "redirect:/admin/manage?pageNum="+pageNum+"&keyword="+keyword;
    }

    @ResponseBody
    @RequestMapping("/assign/to/get/all/auth")
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> authList = authService.getAllAuth();
        return ResultEntity.successWithData(authList);
    }

    @ResponseBody
    @RequestMapping("/assign/to/get/auth/for/role")
    public ResultEntity<List<Integer>> getAuthIdByRoleId( Integer roleId) {
        List<Integer> authIdList = authService.getAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }

    @ResponseBody
    @RequestMapping("/assign/do/save/auth/for/role")
    public ResultEntity<String> doSaveAuthForRole(@RequestBody Map<String,List<Integer>> map) {
        authService.doSaveAuthForRole(map);
        return ResultEntity.successWithOutData();
    }
}
