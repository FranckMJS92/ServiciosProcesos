package com.entregable.ae4.controllers;

import com.entregable.ae4.models.ProductModel;
import com.entregable.ae4.models.CategoryModel;
import com.entregable.ae4.repositories.ProductRepository;
import com.entregable.ae4.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // GET all
    @GetMapping
    public List<ProductModel> getProducts() {
        return productRepository.findAll();
    }

    // GET by id
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {

        Optional<ProductModel> product = productRepository.findById(id);

        if (product.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Producto no encontrado con id: " + id));
        }

        return ResponseEntity.ok(product.get());
    }

    // DELETE - Delete
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Producto no encontrado con id: " + id));
        }

        productRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Producto eliminado con id: " + id));
    }

    // PUT - Update
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductModel productUpdated, @PathVariable Long id) {

        Optional<ProductModel> existingProduct = productRepository.findById(id);

        if (existingProduct.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Producto no encontrado con id: " + id));
        }

        ProductModel product = existingProduct.get();

        // Validar campos si se actualizan
        if (productUpdated.getName() != null && !productUpdated.getName().trim().isEmpty()) {
            product.setName(productUpdated.getName());
        }

        if (productUpdated.getPrice() != null) {
            if (productUpdated.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of("message", "El precio debe ser mayor que 0"));
            }
            product.setPrice(productUpdated.getPrice());
        }

        if (productUpdated.getStock() != null) {
            if (productUpdated.getStock() < 0) {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of("message", "El stock no puede ser negativo"));
            }
            product.setStock(productUpdated.getStock());
        }

        // Validar y actualizar categoría si se cambia
        if (productUpdated.getCategory() != null && productUpdated.getCategory().getId() != null) {
            Optional<CategoryModel> category = categoryRepository.findById(productUpdated.getCategory().getId());
            if (category.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of("message", "La categoría con id " + productUpdated.getCategory().getId() + " no existe"));
            }
            product.setCategory(category.get());
        }

        return ResponseEntity.ok(productRepository.save(product));
    }

    // POST - Create
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductModel product) {

        // 1. Validar campos obligatorios
        if (product.getName() == null || product.getPrice() == null || product.getStock() == null ||
                product.getName().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Faltan campos obligatorios: 'nombre' y 'precio'' son requeridos"));
        }

        // 2. Validar que el precio sea mayor que 0
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "El precio debe ser mayor que 0"));
        }

        // 3. Validar que el stock no sea negativo
        if (product.getStock() < 0) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "El stock no puede ser negativo"));
        }

        // 4. Validar que la categoría asociada exista
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Debe especificar una categoría válida"));
        }

        Optional<CategoryModel> category = categoryRepository.findById(product.getCategory().getId());
        if (category.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "La categoría con id " + product.getCategory().getId() + " no existe"));
        }

        // Asignar la categoría completa al producto
        product.setCategory(category.get());

        ProductModel savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    // GET - Productos con su categoría
    @GetMapping("/with-category")
    public ResponseEntity<?> getProductsWithCategory() {

        List<ProductModel> products = productRepository.findAll();

        if (products.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "No hay productos registrados"));
        }

        return ResponseEntity.ok(products);
    }

    // GET - Productos por categoría
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId) {

        Optional<CategoryModel> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Categoría no encontrada con id: " + categoryId));
        }

        List<ProductModel> products = productRepository.findByCategory(category.get());

        if (products.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "No hay productos en la categoría: " + category.get().getName()));
        }

        return ResponseEntity.ok(products);
    }

    // GET - Productos con precio mayor que...
    @GetMapping("/price-greater-than/{price}")
    public ResponseEntity<?> getProductsByPriceGreaterThan(@PathVariable BigDecimal price) {

        List<ProductModel> products = productRepository.findByPriceGreaterThan(price);

        if (products.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "No hay productos con precio mayor que " + price));
        }

        return ResponseEntity.ok(products);
    }
}