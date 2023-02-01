package com.help.main.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.help.admin.service.RoleService;
import com.help.main.entity.Menu;
import com.help.main.repository.MenuRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MenuService {
	private final RoleService roleService;
    private final MenuRepository menuReopsitory;

    @Transactional(readOnly = true)
    public List<Menu> getMenu() {
        return menuReopsitory.findAll(Sort.by(Sort.Direction.ASC, "menuDislist"));
    }

    public List<Menu> getTopMenu() {
        return menuReopsitory.findTopMenuByMenuPosition("000");
    }

    public List<Menu> getLeftMenu(String userRole) {
        ArrayList<Menu>  menu = new ArrayList<>();

        String strRolePriority = roleService.getPriorityOne(userRole);
        // 공지사항
        menu.addAll(menuReopsitory.findLeftMenuByMenuPositionAndMenuRole("001", strRolePriority, "000"));
        // 상위메뉴
        menu.addAll(menuReopsitory.findLeftMenuByMenuPositionAndMenuRole("100", strRolePriority, "000"));
        // 하위메뉴
        menu.addAll(menuReopsitory.findLeftMenuByMenuPositionAndMenuRole("110",strRolePriority, "000"));
        return menu;
    }
    
}
