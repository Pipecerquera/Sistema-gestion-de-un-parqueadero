package com.gestion_parqueadero.gestion_parqueadero.repository;

import com.gestion_parqueadero.gestion_parqueadero.model.Parqueadero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Long> {
}
