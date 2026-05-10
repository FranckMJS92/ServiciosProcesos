package com.api.usuarios.repositories;

import com.api.usuarios.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    // Metodo para saber si existe un usuario con el email pasado como argumento
    boolean existsByEmail(String email);

    //List<UsuarioModel> findByApellido(String apellido);


}
