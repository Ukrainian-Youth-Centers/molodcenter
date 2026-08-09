package com.katok.molodcenter.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    public static CategoryDto toCategoryDto(Category category) {

        return new CategoryDto(category.getId(), category.getName(), category.getYouthCenter() == null ? null : category.getYouthCenter().getId());
    }

    private Long id;
    private String name;
    private Long youthCenterId;
}
