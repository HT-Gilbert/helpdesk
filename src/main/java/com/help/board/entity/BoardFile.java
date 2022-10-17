package com.help.board.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BoardFile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;						// file db 일련번호
	private long boardId;					// 게시글 일련번호
	private String boardTable;					// 게시판 테이블명
	private String origFileName;			// 파일이름
	private String saveFileName;			// 물리적으로 저장된 파일이름
	private int fileSize;					// 파일크기
	private String fileExt;					// 파일확장자
	private String filePath;				// 파일경로
	private boolean bdelete;				// 삭제여부
	
	@CreatedDate
	private LocalDateTime registerTime;
	
	@Builder
	public BoardFile(Long id, long boardId, String boardTable, String origFileName, String saveFileName, int fileSize, String fileExt,
			String filePath, boolean bdelete, LocalDateTime registerTime) {
		this.id = id;
		this.boardId = boardId;
		this.boardTable = boardTable;
		this.origFileName = origFileName;
		this.saveFileName = saveFileName;
		this.fileSize = fileSize;
		this.fileExt = fileExt;
		this.filePath = filePath;
		this.bdelete = bdelete;
		this.registerTime = registerTime;
	}
}
