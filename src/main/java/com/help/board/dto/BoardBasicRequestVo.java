package com.help.board.dto;


import java.time.LocalDateTime;
import java.util.Map;

import com.help.board.entity.Board;
import com.help.board.entity.BoardBasic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardBasicRequestVo {
    private Map<String, String> h;
	private Map<String, BoardBasicRequestDto> h2;

    public Map<String, String> getH() {
		return h;
	}
	public void setH(Map<String, String> h) {
		this.h = h;
	}
	public Map<String, BoardBasicRequestDto> getH2() {
		return h2;
	}
	public void setH2(Map<String, BoardBasicRequestDto> h22) {
		this.h2 = h22;
	}
}