package com.lch.ssm.service.api;

import com.lch.ssm.entity.Menu;

/**
 * @author lch
 * @create 2020-07-27 9:50
 */
public interface MenuService {

    Menu getMenuTree();

    void addMenuNode(Menu menu);

    void editMenuNode(Menu menu);

    void removeMenuNode(Integer id);
}
