package com.entregable.ae4.repositories;

import com.entregable.ae4.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    // Metodo para verificar si ya existe una categoría con ese nombre
    boolean existsByName(String name);
}
