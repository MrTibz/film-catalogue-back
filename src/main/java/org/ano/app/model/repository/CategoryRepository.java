package org.ano.app.model.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ano.app.model.Category;

import java.util.List;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {

    public List<Category> findAllCategories() {
        return listAll(); // Retourne toutes les catégories
    }
}