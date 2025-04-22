package org.ano.app.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.ano.app.dto.CategoryDTO;
import org.ano.app.service.CategoryService;

import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    CategoryService categoryService;

    @GET
    public List<CategoryDTO> getCategories() {
        return categoryService.getAllCategories();
    }
}
