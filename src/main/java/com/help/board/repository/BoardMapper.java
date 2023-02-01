package com.help.board.repository;

import org.springframework.web.multipart.MultipartFile;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;


import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import com.help.board.dto.BoardBasicRequestVo;
import com.help.board.dto.BoardBasicResponseDto;
import com.help.board.dto.SearchDto;
import com.help.board.dto.SearchResultDto;

public interface BoardMapper {

    static final String INSERT_BOARD_DATA = "INSERT INTO ${h.boardTable} ("
        + "title, content, read_cnt, tag, register_id, register_name, is_delete, is_notice,"
		+ "is_file, is_reply, register_time, update_time) VALUES (#{h2.boardBasicRequestDto.title}, "
        + "#{h2.boardBasicRequestDto.content}, 0, IFNULL(#{h2.boardBasicRequestDto.tag}, '0'), #{h2.boardBasicRequestDto.registerId},"
        + "#{h2.boardBasicRequestDto.registerName},"
        + "IFNULL(#{h2.boardBasicRequestDto.isDelete}, '0'), IFNULL(#{h2.boardBasicRequestDto.isNotice}, '0'),"
        + "IFNULL(#{h2.boardBasicRequestDto.isFile}, '0'), IFNULL(#{h2.boardBasicRequestDto.isReply}, '0'),"
        + "now(), now())";
    
    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     */
    int selectBoardTotalCount(SearchDto params);

    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     */
    //List<Map<String, String>> findAll(HashMap map);
//    @Select("SELECT * FROM	${boardTable} WHERE	is_delete = '0'")
    List<BoardBasicResponseDto> findAll(SearchDto params);
    ArrayList<SearchResultDto> searchFindAll(SearchDto params);
    //Map<String, String> findAll();


        /**
     * 게시글 저장
     * @param boardBasic - 게시글 정보
     */
    @Insert(INSERT_BOARD_DATA) 
    @Options(useGeneratedKeys=true, keyProperty="h2.boardBasicRequestDto.id") 
    long save(BoardBasicRequestVo boardBasicRequestVo);
    long save(BoardBasicRequestVo boardBasicRequestVo, MultipartFile[] files);


    /**
     * 게시글 상세정보 조회
     * @param hm
     * @param id - PK
     * @return 게시글 상세정보
     */
    @Select("SELECT * FROM	${boardTable} WHERE	id = #{listId}")
    BoardBasicResponseDto findById(HashMap<String, Object> hm);
    
    int updateBoardReadCnt(HashMap<String, Object> hm);


    /**
     * 게시글 수정
     * @param params - 게시글 정보
     */
    long updateBoard(BoardBasicRequestVo boardBasicRequestVo);

    /**
     * 게시글 삭제
     */
    int deleteBoardData(HashMap<String, Object> hm);    // 다건 삭제
    int deleteBoard(HashMap<String, Object> hm);        // 다건 삭제처리
    int deleteByIdData(HashMap<String, Object> hm);     // 단건 삭제
    int deleteById(HashMap<String, Object> hm);         // 단건 삭제처리

    // rolemapper 만들면 옮기자. 현재는 임시 테스트로 board에 있음
    List<String> getRoleList(String strRole);
    String getRolePriority(String strRole);

}
