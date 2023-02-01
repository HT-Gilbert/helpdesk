package com.help.admin.repository;

import org.springframework.data.repository.query.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete; 
import java.util.List;
import java.util.HashMap;

import com.help.admin.dto.BoardListDto;
import com.help.main.entity.BoardList;

public interface BoardManageMapper{

  static final String INSERT_BOARD_LIST = "insert into board_list (board_table, board_title, "
  + "board_role, use_search, board_tag_list, use_tag) values ("
  + "#{boardTable}, "
  + "#{boardTitle}, "
  + "#{boardRole}, "
  + "#{useSearch} "
  + "#{boardTagList}, "
  + "#{useTag} "
  + ")";

  static final String CREATE_BOARD = "CREATE TABLE ${boardTable} ("
  + "id bigint NOT NULL AUTO_INCREMENT COMMENT \'PK\', "
  + "title varchar(100) NOT NULL COMMENT \'제목\', "
  + "content text NOT NULL COMMENT \'내용\', "
  + "read_cnt int NOT NULL DEFAULT 0 COMMENT \'조회수\', "
  + "tag varchar(100) NOT NULL DEFAULT 0 COMMENT \'태그\', "
  + "register_id varchar(50) NOT NULL COMMENT \'작성자ID\', "
  + "register_name varchar(50) NOT NULL COMMENT \'작성자이름\', "
  + "is_delete tinyint(1) DEFAULT 0 COMMENT \'삭제여부\',"
  + "is_notice tinyint(1) DEFAULT 0 COMMENT \'공지여부\',"
  + "is_file tinyint(1) DEFAULT 0 COMMENT \'파일여부\',"
  + "is_reply tinyint(1) DEFAULT 0 COMMENT \'댓글여부\',"
  + "register_time datetime DEFAULT NULL COMMENT \'작성일\',"
  + "update_time datetime DEFAULT NULL COMMENT \'수정일\',"
  + "register_ip varchar(20) DEFAULT '0.0.0.0' COMMENT \'작성IP\',"
  + "PRIMARY KEY (id) "
  + ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT= #{boardTitle}";

  //==================================    
    /**
     * 게시판리스트조회
     */
    List<BoardList> findAll();

    /**
     * 검색할 게시판 리스트 조회
     */
    List<BoardListDto> searchBoardListFindAll(List<String> roleList);
    List<BoardListDto> searchBoardListFindOne(String boardTable);

    

    /**
     * 게시판리스트에서 삭제대상 테이블명 조회
     * boardId - 삭제대상 리스트 id
     */
    List<String> getTableName(HashMap<String, Object> hm);
    List<String> getTableTitle(HashMap<String, Object> hm);

    String getTableNameOne(HashMap<String, Object> hm);
    String getTableTitleOne(String boardTable);
    int getTableIdOne(String boardTable);

    /**
     * 게시판 테이블 생성
     * boardListDto - 게시판 테이블 DTO
     */
    @Update(CREATE_BOARD)
    int createBoardTable(BoardListDto boardListDto);

    /**
     * 게시판 리스트에 추가된 게시판 정보 추가
     * boardListDto - 게시판 테이블 DTO
     */
    
    //@Insert(INSERT_BOARD_LIST)
    int insertBoardManageList(BoardListDto boardListDto);

     /**
     * 게시판 리스트에서 게시판 정보 삭제
     */
    int deleteBoardList(@Param("deleteIdList") HashMap<String, Object> deleteIdList);

     /**
     * 게시판 테이블 삭제
     */
    int dropTable(@Param("deleteTableList") List<String> deleteTableList);
  

     /**
     * 조회 테이블 태그 사용유무 확인
     */
    boolean selectUseTag(@Param("boardTable") String boardTable);


    //게시판 추가 삭제시 메뉴리스트 추가삭제 구현해야함.

    
    @Delete("DELETE FROM board_list WHERE id = #{id}")
    void delete(int id);


    int count();
}
