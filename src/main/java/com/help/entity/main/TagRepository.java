package com.help.entity.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TagRepository extends JpaRepository<Tag, Long>{

    static final String DELETE_TAG_ID = "DELETE FROM tag_list "
    + "WHERE TAG_ID IN (:tagId)";

    @Transactional
    @Modifying
    @Query(value = DELETE_TAG_ID, nativeQuery = true)
    public int deleteList(@Param("tagId") Long[] deleteIdList);
}
