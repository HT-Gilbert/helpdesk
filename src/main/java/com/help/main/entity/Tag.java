package com.help.main.entity;

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
@Entity(name = "tag_list")
@Table(name = "tag_list")
public class Tag {
    @Id
    @Column(name = "tag_id")
    private int tagId;
    @Column(name = "tag_name")
    private String tagName;
    @Column(name = "board_id")
    private int boardId;
    @Column(name = "board_priority")
    private String boardPriority;

    @Builder
    public Tag(int tagId, String tagName, int boardId, String boardPriority) {
        super();
        this.tagId = tagId;
        this.tagName = tagName;
        this.boardId = boardId;
        this.boardPriority = boardPriority;
    }   
}
