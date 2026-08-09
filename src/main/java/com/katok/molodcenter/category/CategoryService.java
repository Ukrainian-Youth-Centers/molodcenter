package com.katok.molodcenter.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " undefined"));
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id);

        if (categoryDetails.getName() != null) {
            category.setName(categoryDetails.getName());
        }

        category.setYouthCenter(categoryDetails.getYouthCenter());

        return categoryRepository.save(category);
    }

    public List<Category> getAllGlobalCategories() {
        return categoryRepository.findAllByYouthCenterIsNull();
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
