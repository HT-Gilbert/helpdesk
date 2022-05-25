package com.help.service.main;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.help.entity.main.Category;
import com.help.entity.main.CategoryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
    public List<Category> getCategory() {
        return categoryRepository.findAll();
    }
}
