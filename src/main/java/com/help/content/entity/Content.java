package com.help.content.entity;

import javax.persistence.Entity;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@Entity(name = "content")
@Table(name = "content")
public class Content {

    @Id
    @Column(name = "id")
	private long id;    
    @Column(name = "title")
    private String title;
    @Column(name = "subject")
	private String subject;
    @Column(name = "content")
    private String content;

    @Builder
	public Content(long id, String title, String subject, String content) {
		super();
		this.id = id;
        this.title = title;
        this.subject = subject;
		this.content = content;
	}
}
