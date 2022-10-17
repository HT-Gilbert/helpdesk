package com.help.board.dto;

import java.time.LocalDateTime;

import com.help.board.entity.Board;
import com.help.board.entity.BoardBasic;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardBasicRequestDto {
	/** id */
	private Long id;
	/** 제목 */
	private String title;
	/** 내용 */
	private String content;
	/** 태그 */
	private String tag;
	/** 등록자 */
	private String registerId;
	/** 삭제여부 */
	private boolean isDelete;
	/** 공지여부 */
	private boolean isNotice;
	/** 파일존재여부 */
	private boolean isFile;
	/** 댓글여부 */
	private boolean isReply;
	/** 파일 변경 여부 */
	private String isFileChange;
	/** 파일 인덱스 리스트 */
	private List<Long> fileIds;
	
	public BoardBasic toEntity() {
		return BoardBasic.builder()
			.id(id)
			.title(title)
			.content(content)
			.tag(tag)
			.registerId(registerId)
			.isDelete(isDelete)
			.isNotice(isNotice)
			.isFile(isFile)
			.isReply(isReply)
			.build();
	}

	@Override
	public String toString() {
		return "BoardBasicRequestDto [id=" + id + ", title=" + title + ", content=" + content + ", tag=" + tag + ", registerId=" + registerId
		+ ", isDelete=" + isDelete + ", isNotice=" + isNotice + ", isFile=" + isFile + ", isRefly=" + isReply + "]";
	}
}