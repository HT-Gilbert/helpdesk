package com.help.entity.board.driver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.help.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Driver extends  BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;	
	private String content;
    private String tag;
	private int readCnt;
	private String registerId;
		
		@Builder
	public Driver(Long id, String title, String content, String tag, int readCnt, String registerId) {
		this.id = id; 
		this.title = title;
		this.content = content;
        this.tag = tag;
		this.readCnt = readCnt;
		this.registerId = registerId;
	}
}
