package com.help.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.help.main.entity.Menu;

public interface MenuRepository  extends JpaRepository<Menu, Long>{

    static final String DELETE_MENU_ID = "DELETE FROM menu_list "
    + "WHERE MENU_ID IN (:menuId)";

    @Transactional
    @Modifying
    @Query(value = DELETE_MENU_ID, nativeQuery = true)
    public int deleteList(@Param("menuId") Long[] deleteIdList);
}
