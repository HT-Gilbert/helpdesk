package com.help.board.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.help.board.entity.Board;

import lombok.Getter;

@Getter
public class BoardResponseDto{
	private Long id;
	private String title;
	private String content;
	private int readCnt;
	private String registerId;
	private LocalDateTime registerTime;
	private boolean isNotice = false;
	
	public BoardResponseDto(Board entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.readCnt = entity.getReadCnt();
		this.registerId = entity.getRegisterId();
		this.registerTime = entity.getRegisterTime();
		this.isNotice = entity.isNotice();
	}

	@Override
	public String toString() {
		return "BoardResponseDto [id=" + id + ", title=" + title + ", content=" + content + ", readCnt=" + readCnt
				+ ", registerId=" + registerId + ", registerTime=" + registerTime + ", isNotice=" + isNotice + "]";
	}
	
	public String getRegisterTime() {
		return toStringDateTime(this.registerTime);
	}
	
    public static String toStringDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}