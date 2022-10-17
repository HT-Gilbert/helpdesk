package com.help.main.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.help.main.entity.Menu;
import com.help.main.repository.MenuRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MenuService {
    private final MenuRepository menuReopsitory;

    @Transactional(readOnly = true)
    public List<Menu> getMenu() {
        return menuReopsitory.findAll(Sort.by(Sort.Direction.ASC, "menuDislist"));
    }
    
}
