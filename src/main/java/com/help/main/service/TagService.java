package com.help.main.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.help.board.service.BoardService;
import com.help.main.entity.Tag;
import com.help.main.repository.TagRepository;
import com.help.main.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class TagService {
	
	private final TagRepository tagRepository;

	@Transactional(readOnly = true)
    public List<Tag> getTag(String boardId) {
		List<Tag> tagList = tagRepository.findByboardId(boardId);

		if (tagList.isEmpty() == true) {
			log.error("getTag() testlog");
			return Collections.emptyList();
		}
		else 
			return tagList;

    }

	@Transactional(readOnly = true)
	public List<Tag> getTag() {
        return tagRepository.findAll();
    }
}
