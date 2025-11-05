package com.rosal.service;

import com.rosal.model.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenus();
    Menu create(Menu menu);
}
