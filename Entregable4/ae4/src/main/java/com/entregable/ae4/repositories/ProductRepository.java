package com.entregable.ae4.repositories;

import com.entregable.ae4.models.CategoryModel;
import com.entregable.ae4.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    // Todos los productos de una categoria
    List<ProductModel> findByCategory(CategoryModel category);

    // Contar todos los productos de una categoria
    long countByCategory(CategoryModel category);

    // Buscar productos con precio mayor que
    List<ProductModel> findByPriceGreaterThan(BigDecimal price);
}
