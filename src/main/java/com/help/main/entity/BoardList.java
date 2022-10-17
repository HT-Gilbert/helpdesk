/* 게시판 리스트를 관리
 * 테이블 추가, 삭제 및 검색위해 리스트 관리
 */
package com.help.main.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BoardList {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private int boardId;						// 일련번호
	private String boardTable;					// 게시판 테이블명
	private String boardTitle;					// 게시판 표시명
	private String boardRole;			        // 게시판 접근권한
	private boolean boardSearch;	    		// 게시판 검색 허용여부
	private String boardTagList;			        // 게시판 접근권한
	private boolean boardTagUse;	    		// 게시판 검색 허용여부
	
	@Builder
	public BoardList(int boardId, String boardTable, String boardTitle, String boardRole, boolean boardSearch, String boardTagList, boolean boardTagUse) {
		this.boardId = boardId;
		this.boardTable = boardTable;
		this.boardTitle = boardTitle;
		this.boardRole = boardRole;
		this.boardSearch = boardSearch;
		this.boardTagList = boardTagList;
		this.boardTagUse = boardTagUse;
	}    
	public BoardList(BoardList tempList) {
		this.boardId = tempList.boardId;
		this.boardTable = tempList.boardTable;
		this.boardTitle = tempList.boardTitle;
		this.boardRole = tempList.boardRole;
		this.boardSearch = tempList.boardSearch;
		this.boardTagList = tempList.boardTagList;
		this.boardTagUse = tempList.boardTagUse;
	}
}
