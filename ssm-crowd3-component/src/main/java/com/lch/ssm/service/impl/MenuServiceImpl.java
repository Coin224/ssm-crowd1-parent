package com.lch.ssm.service.impl;

import com.lch.ssm.entity.Menu;
import com.lch.ssm.entity.MenuExample;
import com.lch.ssm.mapper.MenuMapper;
import com.lch.ssm.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lch
 * @create 2020-07-27 9:50
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public Menu getMenuTree() {

        // 从数据库中获取所有的menu
        List<Menu> menuList = menuMapper.selectByExample(new MenuExample());

        // 把menuList中所有的menu和id对应存入到menuMap
        Map<Integer,Menu> menuMap = new HashMap<>();
        for (Menu menu:menuList) {
            Integer id = menu.getId();
            menuMap.put(id,menu);
        }

        // 根节点
        Menu root = null;
        for (Menu menu:menuList) {
            // 获取pid
            Integer pid = menu.getPid();

            // 如果pid为null 则这个节点为父节点
            if (pid == null) {
                root = menu;
                continue;
            }

            // 建立树形结构
            // 找到每个节点的父节点
            Menu fatherNode = menuMap.get(pid);
            fatherNode.getChildren().add(menu);
        }
        // 将根节点返回
        return root;
    }

    @Override
    public void addMenuNode(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void editMenuNode(Menu menu) {
        menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public void removeMenuNode(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
