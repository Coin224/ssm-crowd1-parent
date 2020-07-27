package com.lch.ssm.mvc.controller;

import com.lch.ssm.entity.Menu;
import com.lch.ssm.service.api.MenuService;
import com.lch.ssm.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lch
 * @create 2020-07-27 9:52
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @RequestMapping("/whole/tree")
    @ResponseBody
    public ResultEntity<Menu> getMenuTree() {
        // 获取到根节点
        Menu root = menuService.getMenuTree();

        // 放到ResultEntity返回
        return ResultEntity.successWithData(root);
    }

    @RequestMapping("/do/add")
    @ResponseBody
    public ResultEntity<Menu> addMenuNode(Menu menu) {
        // 获取到根节点
        menuService.addMenuNode(menu);

        // 放到ResultEntity返回
        return ResultEntity.successWithOutData();
    }

    @RequestMapping("/do/edit")
    @ResponseBody
    public ResultEntity<Menu> editMenuNode(Menu menu) {
        // 获取到根节点
        menuService.editMenuNode(menu);

        // 放到ResultEntity返回
        return ResultEntity.successWithOutData();
    }

    @RequestMapping("/do/remove")
    @ResponseBody
    public ResultEntity<Menu> editMenuNode(Integer id) {
        // 获取到根节点
        menuService.removeMenuNode(id);

        // 放到ResultEntity返回
        return ResultEntity.successWithOutData();
    }
}
