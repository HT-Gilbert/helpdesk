package com.help.board.dto;

import com.help.board.entity.Board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {
	private Long id;
	private String title;
	private String content;
	private String registerId;
	private boolean isNotice;
	
	public Board toEntity() {
		return Board.builder()
			.title(title)
			.content(content)
			.registerId(registerId)
			.isNotice(isNotice)
			.build();
	}

	@Override
	public String toString() {
		return "BoardRequestDto [id=" + id + ", title=" + title + ", content=" + content + ", registerId=" + registerId
				+ ", isNotice=" + isNotice + "]";
	}
}