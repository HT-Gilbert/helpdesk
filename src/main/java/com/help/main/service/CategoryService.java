package com.help.main.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.help.main.entity.Category;
import com.help.main.repository.CategoryRepository;

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
