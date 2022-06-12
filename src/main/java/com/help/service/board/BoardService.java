package com.help.service.board;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.help.dto.board.BoardResponseDto;
import com.help.dto.board.BoardRequestDto;
import com.help.entity.board.Board;
import com.help.entity.board.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardFileService boardFileService;
	
	@Transactional
	public boolean save(BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		Board result = boardRepository.save(boardRequestDto.toEntity());
		
		boolean resultFlag = false;
		
		// menuId 0 = 공지사항
		if (result != null) {
			boardFileService.uploadFile(multiRequest, result.getId(), 0);
			resultFlag = true;
		}
		
		return resultFlag;
	}
	
	/*
		트랜잭션에 readOnly=true 옵션을 주면 스프링 프레임워크가 하이버네이트 세션 플러시 모드를 MANUAL로 설정한다.
		이렇게 하면 강제로 플러시를 호출하지 않는 한 플러시가 일어나지 않는다.
		따라서 트랜잭션을 커밋하더라도 영속성 컨텍스트가 플러시 되지 않아서 엔티티의 등록, 수정, 삭제이 동작하지 않고,
		또한 읽기 전용으로, 영속성 컨텍스트는 변경 감지를 위한 스냅샷을 보관하지 않으므로 성능이 향상된다.
	 */
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAll(Integer page, Integer size) throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		/* 정렬 예제
		List<Order> sortList = new LinkedList<>();  
sortList.add(Order.asc("item"));  
sortList.add(Order.desc("id"));

Sort sort = Sort.by(sortList); 


return this.todoRepository.findAll(sort);  
		*/

		List<Order> sortList = new LinkedList<>();
		sortList.add(Order.desc("isNotice"));
		sortList.add(Order.desc("registerTime"));
		Sort sort = Sort.by(sortList); 

		Page<Board> list = boardRepository.findAll(PageRequest.of(page, size, sort));
		//Page<Board> list = boardRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")));
		
		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());
		
		return resultMap;
	}
	
	public HashMap<String, Object> findById(Long id) throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>(); 
		
		boardRepository.updateBoardReadCntInc(id);
		
		BoardResponseDto info = new BoardResponseDto(boardRepository.findById(id).get());
		
	
		resultMap.put("info", info);
		resultMap.put("fileList", boardFileService.findByBoardId(info.getId()));
		
		return resultMap;
	}
	
	public boolean updateBoard(BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		int result = boardRepository.updateBoard(boardRequestDto);
		
		boolean resultFlag = false;
		
		if (result > 0) {
			boardFileService.uploadFile(multiRequest, boardRequestDto.getId(), 0);
			resultFlag = true;
		}
		
		return resultFlag;
	}
	
	public void deleteById(Long id) throws Exception {
		Long[] idArr = {id};
		boardFileService.deleteBoardFileYn(idArr);
		boardRepository.deleteById(id);
	}
	
	public void deleteAll(Long[] deleteIdList) throws Exception {
		boardFileService.deleteBoardFileYn(deleteIdList);
		boardRepository.deleteBoard(deleteIdList);
	}
}
