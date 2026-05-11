package com.entregable.ae4.controllers;

import com.entregable.ae4.models.CategoryModel;
import com.entregable.ae4.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // GET all
    @GetMapping
    public List<CategoryModel> getCategories() {
        return categoryRepository.findAll();
    }

    // GET by id
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {

        Optional<CategoryModel> category = categoryRepository.findById(id);

        // Validar que el id ingresado exista
        if (category.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Categoría no encontrada con id: " + id));
        }

        return ResponseEntity.ok(category.get());
    }

    // DELETE - Delete
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {

        // Validar que el id ingresado exista
        // Se utiliza metodo nativo de JPA
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Categoría no encontrada con id: " + id));
        }

        categoryRepository.deleteById(id);

        return ResponseEntity.ok(Map.of("message", "Categoría eliminada con id: " + id));
    }

    // PUT - Update
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryModel categoryUpdated, @PathVariable("id") Long id) {

        Optional<CategoryModel> existingCategory = categoryRepository.findById(id);

        if (existingCategory.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Categoría no encontrada con id: " + id));
        }


        CategoryModel category = existingCategory.get();

        // Validar que el nombre no pertenezca a otra categoría diferente
        if (!category.getName().equals(categoryUpdated.getName()) &&
                categoryRepository.existsByName(categoryUpdated.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Ya existe otra categoría con el nombre: " + categoryUpdated.getName()));
        }

        category.setName(categoryUpdated.getName());
        category.setDescription(categoryUpdated.getDescription());

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    // POST - Create
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryModel category) {

        // Validar que se envían todos los campos obligatorios
        if (category.getName() == null || category.getDescription() == null ||
                category.getName().trim().isEmpty() || category.getDescription().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()   // HTTP 400
                    .body(Map.of("message", "Faltan campos obligatorios: 'nombre' y 'descripción' son requeridos"));
        }

        // Validar que el nombre no exista ya
        if (categoryRepository.existsByName(category.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Ya existe una categoría con el nombre: " + category.getName()));
        }

        // Si pasa todas las validaciones, guardamos la categoría
        categoryRepository.save(category);

        return ResponseEntity.ok(category);
    }
}
