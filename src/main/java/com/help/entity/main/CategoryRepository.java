package com.help.entity.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    static final String DELETE_CATEGORY_ID = "DELETE FROM category_list "
    + "WHERE CATEGORY_ID IN (:categoryId)";

    @Transactional
    @Modifying
    @Query(value = DELETE_CATEGORY_ID, nativeQuery = true)
    public int deleteList(@Param("categoryId") Long[] deleteIdList);
}