package com.help.admin.dto;

import javax.persistence.Entity;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.Table;
import com.help.main.entity.Tag;

@Getter
@Setter
@NoArgsConstructor
public class TagDto {
    private int tagId;
    private String tagName;
    private int boardId;

    public TagDto(int tagId, String tagName, int boardId) {
        super();
        this.tagId = tagId;
        this.tagName = tagName;
        this.boardId = boardId;
    }  

}
