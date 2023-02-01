package com.help.admin.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.help.admin.dto.BoardListDto;
import com.help.admin.dto.MenuDto;
import com.help.admin.dto.TagDto;
import com.help.main.entity.BoardList;
import com.help.admin.repository.BoardManageMapper;
import com.help.admin.repository.BoardManageRepository;
import com.help.admin.repository.MenuMapper;
import com.help.admin.repository.TagMapper;
import com.help.board.repository.BoardMapper;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardManageService {

	private final BoardManageRepository boardListRepository;
    private final BoardManageMapper boardManageMapper;
    private final BoardMapper boardMapper;

    private final MenuMapper menuMapper;
    private final TagMapper tagMapper;

    // JPA이용
	@Transactional(readOnly = true)
    public List<BoardList> getBoardList() {
        log.error("resultMap :: {}", boardListRepository.findAll());
        return boardListRepository.findAll();
    }

    //==============================================================================
    //MyBatis이용
    public List<BoardList> getBoardListbyMB() {
        
        //resultMap.put("boardList", boardManageMapper.findAll());
        //logger.error("boardManageMapper.findAll :: {}", boardManageMapper.findAll());
        
        return boardManageMapper.findAll();
    }

    public List<BoardList> getBoardSearchList() {
        
        //resultMap.put("boardList", boardManageMapper.findAll());
        //logger.error("boardManageMapper.findAll :: {}", boardManageMapper.findAll());
        
        return boardManageMapper.findAll();
    }

    public boolean createBoardTable(BoardListDto boardListDto) {
        log.error("boardListDto.getBoardTitle :: {}", boardListDto.toString());
        if (boardManageMapper.createBoardTable(boardListDto) == 0) {
            return createBoardAfterProc(boardListDto);
            /*
            int iResult = -1;
            logger.error("boardManageMapper.insertBoardList :: {}", boardListDto);
            // 게시판테이블을 생성됐으면 게시판리스트에 목록을 추가한다.
            boardManageMapper.insertBoardList(boardListDto);
            logger.error("getBoardId :: {}", boardListDto.getBoardId());
            // insert명령이 정상적으로 실행됐으면 boardId 값을 가져오는걸로 정상수행여부 판단
            iResult = boardListDto.getBoardId();
            if(iResult > 0)
                return true;
            else 
                return false;
                 */
        }
        else
            return false;
    }

    public int insertBoardList(BoardListDto boardListDto){
        return boardManageMapper.insertBoardManageList(boardListDto);
    }

    // 게시판 생성 후 리스트 및 메뉴 등록 과정
    boolean createBoardAfterProc(BoardListDto boardListDto) {
        int iResult = -1;
        // 게시판리스트에 목록을 추가한다.
        log.error("testlog 1");
        iResult = insertBoardManageList(boardListDto); 
        
        int boardId = iResult;
        String tempTagList = boardListDto.getBoardTagList().trim();
        
        if(iResult > 0){
        	// 좌측 메뉴 목록 추가
        	iResult = insertMenuList(boardId, boardListDto);            
            
            if(iResult > 0){
            	// 게시판 태그 등록
                if(tempTagList.isEmpty() == false){
                    iResult = insertTagList(boardId, boardListDto.getBoardRole(), tempTagList);
            
                    if(iResult > 0 )
                        return true;
                    else
                        return false;
                }
                else {
                    log.error("등록할 태그 없음");
                    return true;
                }
            }
            else{
                log.error("메뉴등록 실패");
                return false;
            }
        }
        else 
            return false;
    }
    
    // board_list에 게시판 추가
    int insertBoardManageList(BoardListDto boardListDto) {
    	  boardManageMapper.insertBoardManageList(boardListDto);
    	    log.error("getBoardId :: {}", boardListDto.getBoardId());
    	    // insert명령이 정상적으로 실행됐으면 boardId 값을 가져오는걸로 정상수행여부 판단
    	    return boardListDto.getBoardId();
    }
    
    int insertMenuList(int boardId, BoardListDto boardListDto) {
    	// 메뉴에 목록을 추가한다. 음수를 입력하여 검색 조건에서 제외 시킨다.
        // 검색조건 좌측, "110"이고 권한의 MAX(menu_dislist)
        // 게시판명,       좌측(2),           "1"   , 순서 +1,         권한에따라1, 2, 3        ROLE_ADMIN, , "/list"
        // #{menu_name},  #{menu_position}, #{menu_lv},#{menu_dislist}, #{menu_upperid}, #{menu_role}, #{menu_link},
        //boardListDto.getBoardTitle(), 2, 0, ?, 0,boardListDto.getBoardRole(),"/list"
        MenuDto menuDto = new MenuDto(); 
        menuDto.setMenuId(-1);
        menuDto.setMenuPosition("110");		// 좌측메뉴값
        menuDto.setMenuLv("001");			// 첫번째메뉴리스트
        //menuDto.setMenuDislist(-1);
        menuDto.setBoardId(boardId);	// 메뉴의  DB테이블값
        
        if(boardListDto.getBoardRole().equals("ROLE_ADMIN"))
        {
             menuDto.setMenuRole("000");
        }
        else if(boardListDto.getBoardRole().equals("ROLE_MANAGER"))
        {
            menuDto.setMenuRole("010");
        }
        else if(boardListDto.getBoardRole().equals("ROLE_ENG"))
        {
            menuDto.setMenuRole("222");
        }
        else if(boardListDto.getBoardRole().equals("ROLE_USER"))
        {
            menuDto.setMenuRole("025");
        }
        else { 
            menuDto.setMenuRole("030");
        }

        menuDto.setMenuGroup("000");

         menuDto.setMenuName(boardListDto.getBoardTitle());
         menuDto.setMenuLink("/board/list");
         menuDto.setMenuDislist(String.format("%03d", menuMapper.getMaxDislist(menuDto)+1));
         menuDto.setMenuUpperid(menuMapper.getUpperMenuDislist(menuDto));
         
         log.error("menuDto :: {}", menuDto);
         return menuMapper.insertMenuData(menuDto);
    }
    
    int insertTagList(int boardId, String boardRole, String tempTagList) {
    	//ArrayList<ResultData> list = new ArrayList<ResultData>();
    	//ResultData rd = new ResultData();
    	//list.add(count, rd);
    	//count++;
    	String strBoardPriority = boardMapper.getRolePriority(boardRole);
        String[] array = tempTagList.split(",");
        ArrayList<TagDto> list = new ArrayList<>();
                
        for(int i = 0; i < array.length; i++)
        {
            TagDto ld = new TagDto(i+1, array[i], boardId, strBoardPriority);
            //ld.setTagId(i+1);
            //ld.setTagName(array[i]);
            //ld.setBoardId(boardId);  
            list.add(ld);
        }
        return tagMapper.insertTagList(list);
    }
    
  

    public boolean deleteAll(Long[] deleteIdList) throws Exception {
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("deleteIdList", deleteIdList);
      //  hm.put("deleteTableList", deleteTableList);

        // 게시판 리스트에서 삭제된 건수 반환
		int iResult = -1;

        //게시판테이블 삭제는 어떻게 할지...진짜 삭제?
        // 삭제된 리스트의 테이블명 조회 후 테이블 삭제
        List<String> tableList = boardManageMapper.getTableName(hm);
        //List<String> titleList = boardManageMapper.getTableTitle(hm);
        //String[] arry = list.toArray(new String[list.size()]);
        //logger.error("getTableName :: {}", list.size(), arry);

        log.error("tableList :: {}", tableList);
        //logger.error("titleList :: {}", titleList);
        
        
        iResult = boardManageMapper.dropTable(tableList);
        log.error("dropTable :: {}", iResult);
        // 테이블삭제가 정상이라면 게시판 리스트 및 메뉴에서도 삭제한다.
        if(iResult == 0)
        {            
            return deleteAllAfterProc(hm);
        }
        else 
            return false;
	}    
    
    boolean deleteAllAfterProc(HashMap<String, Object> hm) {
        int iResult = -1;
        iResult = menuMapper.deleteMenuList(hm);
        log.error("deleteMenuList iResult :: {}", iResult);            	
        if(iResult > 0){
        	iResult = -1;
            iResult = boardManageMapper.deleteBoardList(hm);
            log.error("deleteBoardList iResult :: {}", iResult);

            if(iResult > 0) {
            	// 외래키 제약조건으로 리스트삭제시 자동삭제됨. 
//            	iResult = -1;
//                iResult = tagMapper.deleteTagList(hm);
//                logger.error("deleteMenuList iResult :: {}", iResult);        

                return true;
            }

        }
        return false;
    }

    /* 
    public BoardList getBoardRow(int i) {
        logger.error("boardManageMapper.findById :: {}", boardManageMapper.findById(1));
        return boardManageMapper.findById(i);
    }
    */
        /*

            public ArrayList<HashMap<String, Object>> getBoardListbyMB() {
        logger.error("boardListRepositorybyMB :: {}", boardListRepositorybyMB.findAll());
        return boardListRepositorybyMB.findAll();
    }
    public ResponseEntity<?> getBoardListbyMB() {
        ResponseDto  responseDto = new ResponseDto();
        responseDto.setResultCode("S0001");
        responseDto.setRes(boardListRepositorybyMB.findAll());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
     */
}
