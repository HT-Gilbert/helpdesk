package com.help.board.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.help.admin.dto.BoardListDto;
import com.help.admin.repository.BoardManageMapper;
import com.help.board.dto.BoardAttachDto;
import com.help.board.dto.BoardAttachRequestDto;
import com.help.board.dto.BoardBasicRequestDto;
import com.help.board.dto.BoardBasicResponseDto;
import com.help.board.dto.SearchDto;
import com.help.board.paging.Pagination;
import com.help.board.repository.BoardAttachMapper;
import com.help.board.repository.BoardMapper;
import com.help.main.entity.BoardList;
import com.help.main.util.FileUtil;
import com.help.board.dto.BoardBasicRequestVo;
import com.help.board.dto.SearchResultDto;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardService {

	private final BoardMapper boardMapper;
	private final BoardFileService boardFileService;

	private final BoardAttachMapper boardAttachMapper;
	private final BoardManageMapper boardManageMapper;
	private final FileUtil fileUtils;

	public int getBoardSearchTableList(String boardTable) throws Exception { 
	
        return boardManageMapper.getTableIdOne(boardTable);
	}
	
	public String getBoardTableName(HashMap<String, Object> hm) throws Exception { 
        return boardManageMapper.getTableNameOne(hm);
	}

	public String getBoardTableName(String boardId) throws Exception { 
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("boardId", boardId); 
        return boardManageMapper.getTableNameOne(hm);
	}
	
	public int getBoardTableId(String boardTable) throws Exception { 
	
        return boardManageMapper.getTableIdOne(boardTable);
	}
	
	public String getBoardTableTitle(String boardTable) throws Exception { 
	
        return boardManageMapper.getTableTitleOne(boardTable);
	}	

	public HashMap<String, Object> searchFindAll(final SearchDto params, String strRole) throws Exception {

		log.error("========================searchFindAll===============================");
		HashMap<String, Object> hm = new HashMap<String, Object>();
		//role_list 에서 우선순위 확인
		List<String> roleList = boardMapper.getRoleList(strRole);
		//logger.error("=========================roleList :: {}", roleList);

		// 검색가능한 테이블 리스트 구하기
			// 권한에 따른 게시판 리스트 반환
		List<BoardListDto> searchBoardList;

		String strTempTable = params.getBoardTable();
		if(strTempTable == null || strTempTable == "")
			searchBoardList = boardManageMapper.searchBoardListFindAll(roleList);
		else 
			searchBoardList = boardManageMapper.searchBoardListFindOne(params.getBoardTable());
			
		int searchBoardCount = searchBoardList.size();
		int searchListCount = 0;
		log.error("=========================searchBoardCount :: {}", searchBoardCount);
		
		//List<BoardBasicResponseDto> list = new ArrayList<>();

		List<SearchResultDto> list = new ArrayList<>();
		List<String> searchMoreFlag = new ArrayList<>();

		// 리스트만큼 키워드로 검색하기
		int i = 0;
		log.error("=========================before for");
		while(i < searchBoardCount)
		{			
			// 테이블검색을 위한 값
			params.setBoardTable(searchBoardList.get(i).getBoardTable());
			params.setBoardTitle(searchBoardList.get(i).getBoardTitle());
			log.error("=========================params :: {}", params.getBoardTable());
			int boardTotalCount = boardMapper.selectBoardTotalCount(params);
			log.error("=========================boardTotalCount :: {}", boardTotalCount);

			// 검색결과가 없으면 리스트에서 제외
			if(boardTotalCount == 0){
				searchBoardList.remove(i);
				searchBoardCount--;
			}
			else{	
				searchListCount = searchListCount + boardTotalCount;
				Pagination pagination = new Pagination(boardTotalCount, params);
				params.setPagination(pagination);

				if(boardTotalCount > params.getRecordSize())
					searchMoreFlag.add("1");
				else
					searchMoreFlag.add("0");

				SearchResultDto tempDto = new SearchResultDto();
				SearchDto tempTable = new SearchDto();
				//                           Integet.toString(searchBoardList.get(i).getBoardId())
				tempTable.setSearchTableInfo(String.valueOf(searchBoardList.get(i).getBoardId()), searchBoardList.get(i).getBoardTable(), searchBoardList.get(i).getBoardTitle());

				
				// List<SearchResultDto> tempList = new ArrayList<>();  
				// if(strTempTable == null)
				// 	tempList = boardMapper.searchFindAll(params);
				// else
				// {
				// 	if(strTempTable.equals(params.getBoardTable()))
				// 		tempList = boardMapper.searchFindAll(params);
				// }
				List<SearchResultDto> tempList = boardMapper.searchFindAll(params);
				
				//logger.error("=========================tempList :: {}", tempList);  
				log.error("=========================tempList :: {}", tempList);  
				list.addAll(tempList);
				//list.searchTable = tempTable;
				//list.add(0, tempTable, tempList);
				//logger.error("=========================list :: {}", list);  
					
				//hm.put("boardTable" + i, params.getBoardTable());
				//hm.put("boardTitle" + i, params.getBoardTitle());
				
				//hm.put("pagination" , pagination);
				i++;
			}
			
		}
		//for(BoardList tempList : searchBoardList)
		/*
		for(i=0; i < searchBoardCount; i++)
		{			
			// 테이블검색을 위한 값
			params.setBoardTable(searchBoardList.get(i).getBoardTable());
			logger.error("=========================params :: {}", params.getBoardTable());
			int boardTotalCount = boardMapper.selectBoardTotalCount(params);
			logger.error("=========================boardTotalCount :: {}", boardTotalCount);

			// 검색결과가 없으면 리스트에서 제외
			if(boardTotalCount == 0){
				searchBoardList.remove(i);
				searchBoardCount--;
				i--;
			}
			else{
				searchListCount = searchListCount + boardTotalCount;
				Pagination pagination = new Pagination(boardTotalCount, params);
				params.setPagination(pagination);

				List<BoardBasicResponseDto> list = boardMapper.findAll(params);      
				logger.error("=========================list :: {}", list);  

				//hm.put("boardTable" + i, params.getBoardTable());
				//hm.put("boardTitle" + i, params.getBoardTitle());
				hm.put("searchList" + i, list);
				hm.put("pagination" + i, pagination);
				i++;
			}
		}
		*/

		if(strTempTable == null || strTempTable == ""){
			params.setBoardTable(null);
		}
		else {
			params.setBoardTable(strTempTable);
		}
		// 게시판 리스트
		hm.put("searchBoardList", searchBoardList);
		// 게시판 수
		hm.put("searchBoardCount", searchBoardList.size());
		log.error("=========================after searchBoardCount :: {}", searchBoardList.size());  
		// 전체 게시물 수
		hm.put("searchListCount", searchListCount);
		// 전체 게시글
		hm.put("searchList" , list);
		// 검색결과가 더있을경우 확인을 위한 값
		hm.put("searchMoreFlag", searchMoreFlag);
		log.error("=========================getTotalRecordCount");
		return hm;
	}
	
	public HashMap<String, Object> findAll(final SearchDto params) throws Exception {

		log.error("========================1===============================");
		log.error("params.getBoardTitle() :: {}", params.getBoardTitle());
		HashMap<String, Object> hm = new HashMap<String, Object>();
		if(params.getBoardTable() == null)
		{	 
			if(params.getBoardTitle() != null)
				hm.put("boardTitle", params.getBoardTitle()); 
		}		 
		if(params.getBoardId() != null)
			hm.put("boardId", params.getBoardId());
		
		if(params.getBoardTable() == null)
			params.setBoardTable(getBoardTableName(hm));

		hm.put("boardTable", params.getBoardTable());

		log.error("params.getBoardTable() :: {}", params.getBoardTable());

		int boardTotalCount = boardMapper.selectBoardTotalCount(params);
		log.error("조회건수 totalCount :: {}", boardTotalCount);
		hm.put("totalCount", boardTotalCount);

        Pagination pagination = new Pagination(boardTotalCount, params);
        params.setPagination(pagination);

        List<BoardBasicResponseDto> list = boardMapper.findAll(params);        

		//boardList = boardMapper.findAll(params);
		//hm.put("boardList", new PagingResponse<>(list, pagination));
		hm.put("boardList", list);
		hm.put("pagination", pagination);
		
		log.error("=========================getTotalRecordCount :: {}", params.getBoardTable());
		log.error("=========================getTotalRecordCount :: {}", pagination.getTotalRecordCount());
		log.error("=========================getTotalPageCount :: {}", pagination.getTotalPageCount());
		log.error("=========================getStartPage :: {}", pagination.getStartPage());
		log.error("=========================getEndPage :: {}", pagination.getEndPage());
		log.error("=========================getLimitStart :: {}", pagination.getLimitStart());
		log.error("=========================isExistNextPage :: {}", pagination.isExistNextPage());
		log.error("=========================isExistPrevPage :: {}", pagination.isExistPrevPage());
		//logger.error("=========================resultMap :: {}",hm);
	
		//	return resultMap;
		return hm;
	}
	/*
	public List<BoardBasicResponseDto> findAll(Integer curPage, Integer pageSize, String boardTable) throws Exception {
		//HashMap<String, Object> resultMap = new HashMap<String, Object>();
		//HashMap<String, Object> map = new HashMap<String, Object>();				
		//map.put("boardTable", boardTable);

		List<BoardBasicResponseDto> boardList = Collections.emptyList();
//		int boardTotalCount = boardMapper.selectBoardTotalCount(params);

//		PaginationInfo paginationInfo = new PaginationInfo(params);
//		paginationInfo.setTotalRecordCount(boardTotalCount);

//		params.setPaginationInfo(paginationInfo);

//		if (boardTotalCount > 0) {
		logger.error("boardMapper.findAll :: {}", boardMapper.findAll(boardTable));
			boardList = boardMapper.findAll(boardTable);
//		}
		//resultMap.put("list", boardList);

		logger.error("boardList :: {}", boardList);
		//logger.error("resultMap :: {}", resultMap);

	//	return resultMap;
	return boardList;
	}
	*/

	
	public boolean updateBoard(String boardTable, BoardBasicRequestDto boardBasicRequestDto, List<MultipartFile> files) throws Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();        

		Map<String, String> h = new HashMap<String, String>();
		h.put("boardTable", boardTable);
		log.error("service =================================action boardBasicRequestDto :: {}", boardBasicRequestDto);
		Map<String, BoardBasicRequestDto> h2 = new HashMap<String, BoardBasicRequestDto>();
		h2.put("boardBasicRequestDto", boardBasicRequestDto);
		
		log.error("=====================================h :: {}", h);
		log.error("=====================================h2 :: {}", h2);
		BoardBasicRequestVo boardBasicRequestVo = new BoardBasicRequestVo();
		boardBasicRequestVo.setH(h);
		boardBasicRequestVo.setH2(h2);
		long result = boardMapper.updateBoard(boardBasicRequestVo);

		log.error("=====================================result :: {}", result);
		log.error("=====================================after h2 :: {}", h2);
		//BoardBasicRequestDto map2 = h2.get("boardBasicRequestDto");
		//logger.error("=====================================id :: {}", map2.getId());		
		//for (String mapkey : h2.keySet()){
		//	logger.error("=====================================key :: {}", mapkey);
		//	logger.error("=====================================value :: {}", h2.get(mapkey));
		//}

		boolean resultFlag = false;
		
		if (result == 1) {
			hm.remove("boardId");
			hm.put("listId", boardBasicRequestDto.getId());
			log.error("=====================================after boardTable :: {}", boardTable);
			int boardId = getBoardTableId(boardTable);
			hm.put("boardId", boardId);
			log.error("=====================================after boardId :: {}", boardId);
			
			//boardFileService.uploadFile(multiRequest, boardBasicRequestDto.getId(), boardTable);
			// 파일이 추가, 삭제, 변경된 경우
			if ("1".equals(boardBasicRequestDto.getIsFileChange())) {
				boardAttachMapper.deleteAttach(hm);
				// fileIds에 포함된 id를 가지는 파일의 삭제여부를 '0'으로 업데이트
				if (CollectionUtils.isEmpty(boardBasicRequestDto.getFileIds()) == false) {
					boardAttachMapper.undeleteAttach(boardBasicRequestDto.getFileIds());
				}
				int queryResult = 1;
				List<BoardAttachDto> fileList = fileUtils.uploadFiles(files, boardBasicRequestDto.getId(), boardId);
				log.error("=====================================upload1 :: {} ", fileList);
				if (CollectionUtils.isEmpty(fileList) == false) {			
					queryResult = boardAttachMapper.insertAttach(fileList);
					if (queryResult < 1) {
						queryResult = 0;
					}
					else
						resultFlag = true;
					log.error("=====================================queryResult :: {}", queryResult);
				}
			}
			resultFlag = true;
		}
				
		return resultFlag;
	}
	
	@Transactional
	public boolean save(String boardTable, BoardBasicRequestDto boardBasicRequestDto, List<MultipartFile> files) throws Exception {
		Map<String, String> h = new HashMap<String, String>();
		h.put("boardTable", boardTable);
		log.error("service =================================action boardBasicRequestDto :: {}", boardBasicRequestDto);
		Map<String, BoardBasicRequestDto> h2 = new HashMap<String, BoardBasicRequestDto>();
		h2.put("boardBasicRequestDto", boardBasicRequestDto);
		
		log.error("=====================================h :: {}", h);
		log.error("=====================================h2 :: {}", h2);
		BoardBasicRequestVo boardBasicRequestVo = new BoardBasicRequestVo();
		boardBasicRequestVo.setH(h);
		boardBasicRequestVo.setH2(h2);
		long result = boardMapper.save(boardBasicRequestVo);

		log.error("=====================================result :: {}", result);
		log.error("=====================================after h2 :: {}", h2);
		//BoardBasicRequestDto map2 = h2.get("boardBasicRequestDto");
		//logger.error("=====================================id :: {}", map2.getId());		
		//for (String mapkey : h2.keySet()){
		//	logger.error("=====================================key :: {}", mapkey);
		//	logger.error("=====================================value :: {}", h2.get(mapkey));
		//}

		boolean resultFlag = false;
		
		if (result == 1) {
			//boardFileService.uploadFile(multiRequest, boardBasicRequestDto.getId(), boardTable);
			//resultFlag = true;
		}
				
		int queryResult = 1;
		List<BoardAttachDto> fileList = fileUtils.uploadFiles(files, boardBasicRequestDto.getId(), getBoardTableId(boardTable));
		log.error("=====================================upload1 :: {} ", fileList);
		if (CollectionUtils.isEmpty(fileList) == false) {			
			queryResult = boardAttachMapper.insertAttach(fileList);
			if (queryResult < 1) {
				queryResult = 0;
			}
			else
				resultFlag = true;
			log.error("=====================================queryResult :: {}", queryResult);
		}
		log.error("=====================================upload12");
		return resultFlag;
	}

    public boolean deleteAll(String boardTable, Long[] deleteIdList) throws Exception {
        HashMap<String, Object> hm = new HashMap<String, Object>(); 	   
		hm.put("boardTable", boardTable);
		hm.put("deleteIdList", deleteIdList);
		//HashMap<String, Object> map = new HashMap<String, Object>();
		//map.put("boardTable", boardTable);
		//map.put("deleteIdList", deleteIdList);

        // 게시판 리스트에서 삭제된 건수 반환
		int iResult = -1;
		
		
		iResult = boardMapper.deleteBoard(hm);			// 삭제표시
		//iResult = boardMapper.deleteBoardData(hm);			// 삭제

		log.error("deleteBoardData iResult :: {}", iResult);
        if(iResult > 0){
			iResult = -1;
			hm.put("boardId", getBoardTableId(boardTable));
			iResult = boardAttachMapper.deleteAttachList(hm);	// 삭제표시
			//iResult = boardAttachMapper.deleteAttachListData(hm);	// 삭제			
			log.error("=======================deleteBoardIsDelete iResult :: {}", iResult);
            return true;
        }
        return false;
    }

	public boolean deleteById(String boardTable, Long deleteIdList) throws Exception {
	HashMap<String, Object> hm = new HashMap<String, Object>();        
	hm.put("boardTable", boardTable);
	hm.put("deleteId", deleteIdList);

	// 게시판 리스트에서 삭제된 건수 반환
	int iResult = -1;
	
	iResult = boardMapper.deleteById(hm);
	log.error("deleteById iResult :: {}", iResult);
	if(iResult > 0){
		iResult = -1;
		hm.put("boardId", getBoardTableId(boardTable));
		iResult = boardAttachMapper.deleteAttach(hm);		// 삭제표시
		//iResult = boardAttachMapper.deleteAttachData(hm);		// 삭제
		log.error("=======================deleteBoardIsDelete iResult :: {}", iResult);

		if(iResult >= 0){
			return true;
		}
	}
	return false;
}

	public HashMap<String, Object> findById(String boardTable, long id, String isModify) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(); 
		HashMap<String, Object> hm = new HashMap<String, Object>();        

		hm.put("boardTable", boardTable);
		hm.put("boardId", getBoardTableId(boardTable));		
		hm.put("listId", id);
		log.error("=====================================hm :: {}", hm);
		
		int iresult = -1;
		if(isModify == "0")		// 글 수정이아닌 단순 조회일 경우만 카운트 증가
		{
			iresult = boardMapper.updateBoardReadCnt(hm);
			log.error("=====================================iresult :: {}", iresult);
		}
		
		//logger.error("=====================================findById :: {}", boardMapper.findById(hm));

		resultMap.put("list", boardMapper.findById(hm));
		resultMap.put("fileList", getAttachFileList(hm));
		resultMap.put("boardTitle", getBoardTableTitle(boardTable));
		log.error("=====================================getAttachFileList :: {}", getAttachFileList(hm));
		//resultMap.put("fileList", boardFileService.findByBoardIdAndBoardTable(id, boardTable));

		return resultMap;
	}

	/*
	public void deleteById(Long id) throws Exception {
		Long[] idArr = {id};
		boardFileService.updateFileIsDelete(idArr);
		//boardMapper.deleteById(id);
	}
	 */
	public void deleteAll(Long deleteIdList) throws Exception {
		//boardFileService.deleteBoardFileYn(deleteIdList);
		//boardMapper.deleteBoard(deleteIdList);
		//boardMapper.deleteById(deleteIdList);
	}

	public List<BoardAttachDto> getAttachFileList(HashMap<String, Object> hm) {

		int fileTotalCount = boardAttachMapper.selectAttachTotalCount(hm);
		if (fileTotalCount < 1) {
			return Collections.emptyList();
		}
		return boardAttachMapper.selectAttachList(hm);
	}	

	public BoardAttachRequestDto getAttachDetail(Long id) {
		return boardAttachMapper.selectAttachDetail(id);
	}
}
