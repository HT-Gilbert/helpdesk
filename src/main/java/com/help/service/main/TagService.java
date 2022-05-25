package com.help.service.main;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.help.entity.main.Tag;
import com.help.entity.main.TagRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagService {
	private final TagRepository tagRepository;

	@Transactional(readOnly = true)
    public List<Tag> getTag() {
        return tagRepository.findAll();
    }

}
