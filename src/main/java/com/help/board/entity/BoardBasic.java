package com.help.board.entity;

import java.time.LocalDateTime;

/*
 * 게시판 기본 ENtity 실제테이블은 사용하지 않음
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.help.main.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BoardBasic{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;	
	private String content;
	private int readCnt;
	private String tag;
	private String registerId;
	private String registerName;
	private boolean isDelete;
	private boolean isNotice;
	private boolean isFile;
	private boolean isReply;
	private String registerTime;
	private String updateTime;

	/*
    @Transient @Builder.Default
    private List<BoardReply> replies = Collections.emptyList();
    @Transient @Builder.Default
    private long replyCount = 0L;
    @Transient @Builder.Default
    private String authorNickname = null;
	*/
	//private boolean isNocice;
	//private boolena isFrozen;

	@Builder
	public BoardBasic(Long id, String title, String content, int readCnt, String tag, String registerId, String registerName
				, boolean isDelete, boolean isNotice, boolean isFile, boolean isReply, String registerTime
				, String updateTime) {
		this.id = id; 
		this.title = title;
		this.content = content;
		this.readCnt = readCnt;
		this.tag = tag;
		this.registerId = registerId;
		this.registerName = registerName;
		this.isDelete =  isDelete;
		this.isNotice =  isNotice;
		this.isFile =  isFile;
		this.isReply =  isReply;
		}
}
