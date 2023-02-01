package com.help.main.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.help.main.entity.Menu;

public interface MenuRepository  extends JpaRepository<Menu, Long>{

    static final String DELETE_MENU_ID = "DELETE FROM menu_list "
    + "WHERE MENU_ID IN (:menuId)";

    static final String FIND_TOP_MENU_LIST = "SELECT * FROM menu_list "
    + "WHERE MENU_POSITION = :menuPosition order by menu_dislist";

    static final String FIND_LEFT_MENU_LIST = "SELECT * FROM menu_list "
    + "WHERE MENU_POSITION = :menuPosition and CAST(MENU_ROLE as signed) >= CAST(:menuRole as signed)"
    + " and MENU_GROUP = :menuGroup order by menu_dislist";

    static final String FIND_ADMIN_MENU_LIST = "SELECT * FROM menu_list "
    + "WHERE MENU_POSITION = :menuPosition order by menu_dislist";

    @Transactional
    @Modifying
    @Query(value = DELETE_MENU_ID, nativeQuery = true)
    public int deleteList(@Param("menuId") Long[] deleteIdList);

    @Query(value = FIND_TOP_MENU_LIST, nativeQuery = true)
    public List<Menu> findTopMenuByMenuPosition(@Param("menuPosition") String menuPosition);

    @Query(value = FIND_LEFT_MENU_LIST, nativeQuery = true)
    public List<Menu> findLeftMenuByMenuPositionAndMenuRole(@Param("menuPosition")String menuPosition, @Param("menuRole")String menuRole, @Param("menuGroup") String menuGroup);

    @Query(value = FIND_LEFT_MENU_LIST, nativeQuery = true)
    public List<Menu> findAdminMenuByMenuPosition(@Param("menuPosition")String menuPosition, @Param("menuGroup") String menuGroup);

}
