package com.gestion_parqueadero.gestion_parqueadero.repository;

import com.gestion_parqueadero.gestion_parqueadero.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
}