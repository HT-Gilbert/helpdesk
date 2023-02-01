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
import com.help.admin.service.RoleService;
import com.help.main.repository.TagRepository;
import com.help.main.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class TagService {
	
	private final RoleService roleService;
	private final TagRepository tagRepository;

	@Transactional(readOnly = true)
    public List<Tag> getBoardTag(String boardId) {
		List<Tag> tagList = tagRepository.findByboardId(boardId);

		if (tagList.isEmpty() == true) {
			log.error("getBoardTag() is empty");
			return Collections.emptyList();
		}
		else 
			return tagList;

    }

	@Transactional(readOnly = true)
    public List<Tag> getUserTag(String userRole) {
		String strRolePriority = roleService.getPriorityOne(userRole);
		List<Tag> tagList = tagRepository.findByboardPriority(strRolePriority);

		if (tagList.isEmpty() == true) {
			log.error("getUserTag() is empty");
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
