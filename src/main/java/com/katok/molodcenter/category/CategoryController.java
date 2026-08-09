package com.katok.molodcenter.category;

import com.katok.molodcenter.youthcenter.YouthCenter;
import com.katok.molodcenter.youthcenter.YouthCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private YouthCenterService youthCenterService;

    @GetMapping
    public List<Category> getAllGlobalCategories() {
        return categoryService.getAllGlobalCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryCreateDto categoryCreateDto) {
        Category category = Category.builder()
                .name(categoryCreateDto.getName())
                .build();

        if (categoryCreateDto.getYouthCenterId() != null) {
            YouthCenter youthCenter = youthCenterService.getYouthCenterById(categoryCreateDto.getYouthCenterId());

            category.setYouthCenter(youthCenter);
        }

        categoryService.addCategory(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryDto.toCategoryDto(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryCreateDto categoryCreateDto) {
        Category categoryDetails = Category.builder()
                .name(categoryCreateDto.getName())
                .build();

        if (categoryCreateDto.getYouthCenterId() != null) {
            YouthCenter youthCenter = youthCenterService.getYouthCenterById(categoryCreateDto.getYouthCenterId());

            categoryDetails.setYouthCenter(youthCenter);
        }

        Category category = categoryService.updateCategory(id, categoryDetails);

        return CategoryDto.toCategoryDto(category);
    }
}
