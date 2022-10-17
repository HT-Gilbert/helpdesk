package com.help.admin.repository;

import org.springframework.data.repository.query.Param;
import java.util.HashMap;

import com.help.admin.dto.MenuDto;

public interface MenuMapper {
    int insertMenuData(MenuDto menuDto);
    MenuDto findOne(MenuDto menuDto);
    int getMaxDislist(MenuDto menuDto);
    String getUpperMenuDislist(MenuDto menuDto);

    /**
    * 게시판 리스트에서 게시판 정보 삭제
    */
    int deleteMenuList(HashMap<String, Object> deleteIdList);

}
