package org.ano.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ano.app.dto.CategoryDTO;
import org.ano.app.dto.FilmDTO;
import org.ano.app.model.Category;
import org.ano.app.model.Film;
import org.ano.app.model.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAllCategories()
                .stream()
                .map(category -> new CategoryDTO(category.getCategory_id(), category.getName()))
                .collect(Collectors.toList());
    }



}

