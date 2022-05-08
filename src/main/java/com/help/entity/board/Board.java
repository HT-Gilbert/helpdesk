package com.help.entity.board;

import org.springframework.data.annotation.Transient;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.help.entity.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Board extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	private int readCnt;
	private String registerId;
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
	public Board(Long id, String title, String content, int readCnt, String registerId) {
		this.id = id; 
		this.title = title;
		this.content = content;
		this.readCnt = readCnt;
		this.registerId = registerId;
	}
}
