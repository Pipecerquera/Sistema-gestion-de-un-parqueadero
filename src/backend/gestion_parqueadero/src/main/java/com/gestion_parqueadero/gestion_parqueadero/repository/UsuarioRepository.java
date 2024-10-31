package com.gestion_parqueadero.gestion_parqueadero.repository;

import com.gestion_parqueadero.gestion_parqueadero.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
