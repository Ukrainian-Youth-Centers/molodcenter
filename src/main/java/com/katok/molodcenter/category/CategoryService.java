package com.katok.molodcenter.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

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

    public Page<Category> getAllGlobalCategories(Pageable pageable) {
        return categoryRepository.findAllByYouthCenterIsNull(pageable);
    }

    public Page<Category> getCategoriesByYouthCenterId(Long youthCenterId, Pageable pageable) {
        return categoryRepository.findAllByYouthCenterId(youthCenterId, pageable);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
