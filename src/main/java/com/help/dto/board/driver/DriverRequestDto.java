package com.help.dto.board.driver;

import com.help.entity.board.driver.Driver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DriverRequestDto {
    private Long id;
	private String title;	
	private String content;
    private String tag;
	private int readCnt;
	private String registerId;

    public Driver toEntity() {
		return Driver.builder()
			.title(title)
			.content(content)
			.registerId(registerId)
			.tag(tag)
			.build();
	}

	@Override
	public String toString() {
		return "BoardRequestDto [id=" + id + ", title=" + title + ", content=" + content + ", tag=" + tag + "]";
	} 
}
