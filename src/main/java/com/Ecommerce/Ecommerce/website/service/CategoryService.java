package com.Ecommerce.Ecommerce.website.service;

import com.Ecommerce.Ecommerce.website.entity.tenant.Category;
import com.Ecommerce.Ecommerce.website.repository.tenant.CategoryRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> list() {
        return categoryRepository.findAll();
    }

    public Category create(Category category) {
        category.setId(null);
        return categoryRepository.save(category);
    }

    public Category update(UUID id, Category input) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setName(input.getName());
        category.setParent(input.getParent());
        return categoryRepository.save(category);
    }

    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }
}

