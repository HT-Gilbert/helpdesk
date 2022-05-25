package com.help.service.main;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.help.entity.main.Menu;
import com.help.entity.main.MenuReopsitory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MenuService {
    private final MenuReopsitory menuReopsitory;

    @Transactional(readOnly = true)
    public List<Menu> getMenu() {
        return menuReopsitory.findAll();
    }
    
}
