package com.help.content.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.help.content.entity.Content;
import com.help.content.repository.ContentRepository;
import com.help.main.entity.Contact;
import com.help.main.repository.ContactRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ContentService {
	
	private final ContentRepository contentRepository;

    public HashMap<String, Object> findById(Long contentId) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(); 
		
        Optional<Content> contentOptional = contentRepository.findById(contentId);
        Content content = contentOptional.get();
        // or Content content = contentRepository.findById(contentId).get();
        

        log.error("=====================================contentRepository : {}", content);

		resultMap.put("content", content);
		
		//resultMap.put("fileList", boardFileService.findByBoardIdAndBoardTable(id, boardTable));

		return resultMap;
	}
}
