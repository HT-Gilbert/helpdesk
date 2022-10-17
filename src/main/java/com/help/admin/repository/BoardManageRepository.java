package com.help.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.help.main.entity.BoardList;

public interface BoardManageRepository extends JpaRepository<BoardList, Long>{

    static final String DELETE_TAG_ID = "DELETE FROM board_list "
    + "WHERE TAG_ID IN (:boardListId)";

    @Transactional
    @Modifying
    @Query(value = DELETE_TAG_ID, nativeQuery = true)
    public int deleteList(@Param("boardListId") Long[] deleteIdList);
}