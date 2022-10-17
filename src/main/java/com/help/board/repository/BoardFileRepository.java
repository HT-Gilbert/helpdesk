package com.help.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.help.board.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
	
	static final String SELECT_FILE_ID= "SELECT * FROM board_file "
			+ "WHERE BOARD_ID = :boardId AND IS_DELETE != true";

	static final String SELECT_FILE_ID_TABLE= "SELECT * FROM board_file "
	+ "WHERE BOARD_ID = :boardId AND BOARD_TABLE = :boardTable AND IS_DELETE != true";
	
	static final String UPDATE_IS_DELETE_YN= "UPDATE board_file "
			+ "SET IS_DELETE = true "
			+ ", DELETE_TIME = now() "
			+ "WHERE ID IN (:deleteIdList)";
	
	static final String DELETE_BOARD_IS_DELETE= "UPDATE board_file "
			+ "SET IS_DELETE = true "
			+ ", DELETE_TIME = now() "
			+ "WHERE BOARD_TABLE = (:boardTable)"
			+ "AND BOARD_ID IN (:boardIdList)";
	
	@Query(value = SELECT_FILE_ID, nativeQuery = true)
	public List<BoardFile> findByBoardId(@Param("boardId") Long boardId);
	
	@Query(value = SELECT_FILE_ID_TABLE, nativeQuery = true)
	public List<BoardFile> findByBoardIdAndBoardTable(@Param("boardId") Long boardId, @Param("boardTable") String boardTable);

	@Transactional
	@Modifying
	@Query(value = UPDATE_IS_DELETE_YN, nativeQuery = true)
	public int updateFileIsDelete(@Param("deleteIdList") Long[] deleteIdList);
	
	@Transactional
	@Modifying
	@Query(value = DELETE_BOARD_IS_DELETE, nativeQuery = true)
	public int deleteBoardIsDelete(@Param("boardTable") String boardTable, @Param("boardIdList") Long[] boardIdList);
}
