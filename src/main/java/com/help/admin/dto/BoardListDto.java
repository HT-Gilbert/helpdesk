/* 게시판 리스트를 관리
 * 테이블 추가, 삭제 및 검색위해 리스트 관리
 */
package com.help.admin.dto;

import com.help.main.entity.BoardList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardListDto {
	private int boardId;						// 일련번호
	private String boardTable;					// 게시판 테이블명
	private String boardTitle;					// 게시판 표시명
	private String boardRole;			        // 게시판 접근권한
	private boolean boardSearch;	    		// 게시판 검색 허용여부
	private String boardTagList;					// 게시판 테크 리스트
	private boolean boardTagUse;				// 태크사용여부

	public BoardList toEntity() {
		return BoardList.builder()
			.boardId(boardId)
			.boardTable(boardTable)
			.boardTitle(boardTitle)
			.boardRole(boardRole)
            .boardSearch(boardSearch)
            .boardTagList(boardTagList)
			.boardTagUse(boardTagUse)		
			.build();
	}

	public BoardListDto(BoardListDto tempList) {
		this.boardId = tempList.boardId;
		this.boardTable = tempList.boardTable;
		this.boardTitle = tempList.boardTitle;
		this.boardRole = tempList.boardRole;
		this.boardSearch = tempList.boardSearch;
		this.boardTagList = tempList.boardTagList;
		this.boardTagUse = tempList.boardTagUse;
	}

}
