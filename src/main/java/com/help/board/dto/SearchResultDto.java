package com.help.board.dto;

import com.help.board.dto.BoardBasicResponseDto;
import com.help.board.dto.SearchDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.help.board.paging.Pagination;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchResultDto{
    private String searchBoardTable;
    private String searchBoardTitle;
    // BoardBasicResponseDto의 멤버리스트
    private Long id;
	private String title;
	private String content;
	private int readCnt;
	private String tag;
	private String registerId;
	private boolean isDelete = false;
	private boolean isNotice = false;
	private boolean isFile = false;
	private boolean isReply = false;
	private String registerTime;
	private String updateTime;

 }