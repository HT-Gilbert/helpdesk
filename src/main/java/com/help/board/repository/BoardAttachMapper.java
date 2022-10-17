package com.help.board.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.help.board.dto.BoardAttachDto;
import com.help.board.dto.BoardAttachRequestDto;

@Mapper
public interface BoardAttachMapper {
    
	public int insertAttach(List<BoardAttachDto> attachList);

	public BoardAttachRequestDto selectAttachDetail(long id);

	/**
     * 파일삭제
     */
	public int deleteAttach(HashMap<String, Object> hm);			// 단건 파일 삭제표시
	public int deleteAttachData(HashMap<String, Object> hm);		// 단건 파일 삭제
	public int deleteAttachList(HashMap<String, Object> hm);		// 다건 파일 삭제표시
	public int deleteAttachListData(HashMap<String, Object> hm);	// 다건 파일 삭제
	
	public List<BoardAttachDto> selectAttachList(HashMap<String, Object> hm);
	
	public int selectAttachTotalCount(HashMap<String, Object> hm);

	public int undeleteAttach(List<Long> ids);
	
}
