/* 게시판 리스트를 관리
 * 테이블 추가, 삭제 및 검색위해 리스트 관리
 */
package com.help.main.dto;

import com.help.main.entity.BoardList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardListDto {
	private int boardId;						// 일련번호
	private String boardTable;					// 게시판 테이블명
	private String boardTitle;					// 게시판 표시명
	private String boardRole;			        // 게시판 접근권한
	private boolean useSearch;	    		// 게시판 검색 허용여부

	public BoardList toEntity() {
		return BoardList.builder()
			.boardId(boardId)
			.boardTable(boardTable)
			.boardTitle(boardTitle)
			.boardRole(boardRole)
            .useSearch(useSearch)
			.build();
	}

	@Override
	public String toString() {
		return "BoardListDto [boardId=" + boardId + ", boardTable=" + boardTable + ", boardTitle=" + boardTitle + ", boardRole=" + boardRole
				+ ", useSearch=" + useSearch + "]";
	}
}
