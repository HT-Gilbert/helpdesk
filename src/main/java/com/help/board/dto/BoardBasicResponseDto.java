package com.help.board.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.help.board.entity.BoardBasic;

import lombok.Getter;

@Getter
public class BoardBasicResponseDto{
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
	/*
	public BoardBasicResponseDto(BoardBasic entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.readCnt = entity.getReadCnt();
		this.registerId = entity.getRegisterId();
		this.isDelete = entity.isDelete();
		this.isNotice = entity.isNotice();
		this.isFile = entity.isFile();
		this.isReply = entity.isReply();
		this.registerTime = entity.getRegisterTime();
		this.updateTime = entity.getUpdateTime();

	}

	@Override
	public String toString() {
		return "BoardResponseDto [id=" + id + ", title=" + title + ", content=" + content + ", readCnt=" + readCnt
				+ ", registerId=" + registerId + ", isDelete=" + isDelete + ", isNotice=" + isNotice
				+ ", isFile=" + isFile  + ", isReply=" + isReply + ", registerTime=" + registerTime 
				+ ", updateTime=" + updateTime + "]";
	}
	
	public String getRegisterTime() {
		return toStringDateTime(this.registerTime);
	}

	public String getUpdateTime() {
		return toStringDateTime(this.updateTime);
	}
	
    public static String toStringDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
	 */
}