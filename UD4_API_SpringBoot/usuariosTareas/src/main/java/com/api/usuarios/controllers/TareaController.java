package com.api.usuarios.controllers;

import com.api.usuarios.models.TareaModel;
import com.api.usuarios.models.UsuarioModel;
import com.api.usuarios.repositories.TareaRepository;
import com.api.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tareas")
@CrossOrigin(origins = "*")
public class TareaController {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Todas las tareas de un usuario
    // GET http://localhost:4000/tareas/usuario/4
    @GetMapping("/usuario/{usuarioId")
    public ResponseEntity<?> getTareasByUsuario(@PathVariable Long usuarioId) {

        // Comprobar que exista el usuario
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Usuario no encontrado"));
        }

        List<TareaModel> tareasUser = tareaRepository.findByUsuario(usuarioOpt.get());

        return ResponseEntity.ok(tareasUser);
    }

    // Crear una nueva tarea
    // POST http://localhost:4000/tareas/usuario/4
    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> crearTarea(@PathVariable Long usuarioId, @RequestBody TareaModel tarea) {

        // Comprobar que exista el usuario
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Usuario no encontrado"));
        }

        // Comprobamos campos obligatorios
        if (tarea.getTitulo() == null || tarea.getTitulo().isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "El titulo es obligatorio"));
        }


        tarea.setUsuario(usuarioOpt.get());
        tareaRepository.save(tarea);
        return ResponseEntity.ok(tarea);
    }

    // Actualizar tarea
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTarea(@PathVariable Long id, @RequestBody TareaModel tareaModificado) {

        // Comproibar que la tarea existe
        Optional<TareaModel> tareaOpt = tareaRepository.findById(id);

        if (tareaOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Tarea no encontrada"));
        }

        TareaModel tarea = tareaOpt.get();

        if (tareaModificado.getTitulo() != null) {
            tarea.setTitulo(tareaModificado.getTitulo());
        }

        if (tareaModificado.getDescripcion() != null) {
            tarea.setDescripcion(tareaModificado.getDescripcion());
        }

        if (tareaModificado.getCompletada() != null) {
            tarea.setCompletada(tareaModificado.getCompletada());
        }

        tareaRepository.save(tarea);
        return ResponseEntity.ok(tarea);
    }

    // Eliminar tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTarea(@PathVariable Long idTarea) {
        // Comprobar si la tarea a modificar existe
        Optional<TareaModel> tareaOpt = tareaRepository.findById(idTarea);

        if (tareaOpt.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Tarea con ID" + idTarea + " no encontrada"));
        }

        try {
            tareaRepository.deleteById(idTarea);

            return ResponseEntity.ok()
                    .body(Map.of("message", "Tarea con ID" + idTarea + " eliminada correctamente"));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Error al elimina la tarea con ID " + idTarea));
        }
    }
}
