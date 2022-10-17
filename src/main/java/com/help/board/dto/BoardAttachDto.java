package com.help.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardAttachDto {
    	/** 파일 번호 (PK) */
	private Long id;

	/** 게시글 번호 (FK) */
	private Long listId;

    /** 게시글 테이블 ID (FK) */
	private int boardId;

	/** 원본 파일명 */
	private String originalName;

	/** 저장 파일명 */
	private String saveName;

	/** 파일 크기 */
    private long fileSize;

    /** 파일 확장자 */
    private String fileExt;

	/** 파일 삭제여부 */
    private boolean bdelete;

}
