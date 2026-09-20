package com.katok.molodcenter.category;

import com.katok.molodcenter.youthcenter.YouthCenter;
import com.katok.molodcenter.youthcenter.YouthCenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final YouthCenterService youthCenterService;

    @GetMapping
    public Page<CategoryDto> getAllGlobalCategories(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);

        return categoryService.getAllGlobalCategories(pageable)
                .map(CategoryDto::toCategoryDto);
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return CategoryDto.toCategoryDto(categoryService.getCategoryById(id));
    }

    @GetMapping("/external-id/{externalId}")
    public CategoryDto getCategoryByExternalId(@PathVariable String externalId) {
        return CategoryDto.toCategoryDto(categoryService.getCategoryByExternalId(externalId));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryCreateDto categoryCreateDto) {
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
    public CategoryDto updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryCreateDto categoryCreateDto) {
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
