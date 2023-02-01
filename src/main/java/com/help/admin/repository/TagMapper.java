package com.help.admin.repository;

import org.springframework.data.repository.query.Param;
import java.util.HashMap;
import java.util.List;

import com.help.admin.dto.TagDto;

public interface TagMapper {
    int insertTagList(List<TagDto> tagList);
    int selectTagList(List<TagDto> tagList);
    int deleteTagList(HashMap<String, Object> deleteIdList);

}
