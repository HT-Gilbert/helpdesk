package com.help.board.dto;

import com.help.board.paging.Pagination;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDto {
    private String boardId;        // 검색할 테이블 ID
    private String boardTable;     // 검색할 테이블 
    private String boardTitle;     // 검색할 테이블명
    private int page;             // 현재 페이지 번호
    private int recordSize;       // 페이지당 출력할 데이터 개수
    private int pageSize;         // 화면 하단에 출력할 페이지 사이즈
    private String keyword;       // 검색 키워드
    private String searchType;    // 검색 유형
    private Pagination pagination;    // 페이지네이션 정보

    public SearchDto() {
        this.page = 1;
        this.recordSize = 10;
        this.pageSize = 10;
    }

    public void setSearchTableInfo(String boardId, String boardTable, String boardTitle){
        this.boardId = boardId;
        this.boardTable = boardTable;
        this.boardTitle = boardTitle;
    }
}