package com.lch.ssm.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.lch.ssm.entity.Role;
import com.lch.ssm.service.api.RoleService;
import com.lch.ssm.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.List;

/**
 * @author lch
 * @create 2020-07-22 0:51
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;


    @ResponseBody
    @RequestMapping("role/manage")
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                          @RequestParam(value = "keyword",defaultValue = "") String keyword) {
        // 调用service方法获取分页数据
        PageInfo<Role> pageInfo = roleService.getPageInfo(keyword, pageNum, pageSize);

        // 封装到ResultEntity
        return ResultEntity.successWithData(pageInfo);
    }

    @ResponseBody
    @RequestMapping("/role/addRole")
    public ResultEntity<String> addRole(Role role) {
        roleService.addRole(role);
        return ResultEntity.successWithOutData();
    }

    @ResponseBody
    @RequestMapping("/role/editRole")
    public ResultEntity<String> editRole(Role role) {
        roleService.editRole(role);
        return ResultEntity.successWithOutData();
    }

    @ResponseBody
    @RequestMapping("/role/removeRole")
    public ResultEntity<String> removeRole(@RequestBody List<Integer> roleIdList) {
        roleService.removeRole(roleIdList);
        return ResultEntity.successWithOutData();
    }
}
