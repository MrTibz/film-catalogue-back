package org.ano.app.mapper;

import org.ano.app.dto.CategoryDTO;
import org.ano.app.model.Category;

public class CategoryMapper {

    private CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.getCategory_id(),
                category.getName()
        );
    }

}
