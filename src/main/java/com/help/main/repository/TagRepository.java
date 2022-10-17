package com.help.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.help.board.entity.BoardFile;
import com.help.main.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{

    static final String DELETE_TAG_ID = "DELETE FROM tag_list "
    + "WHERE TAG_ID IN (:tagId)";

	static final String SELECT_TAG_BOARDID= "SELECT * FROM tag_list "
			+ "WHERE BOARD_ID = :boardId";
    @Transactional
    @Modifying
    @Query(value = DELETE_TAG_ID, nativeQuery = true)
    public int deleteList(@Param("tagId") Long[] deleteIdList);
    
	@Query(value = SELECT_TAG_BOARDID, nativeQuery = true)
	public List<Tag> findByboardId(@Param("boardId") String boardId);
}
