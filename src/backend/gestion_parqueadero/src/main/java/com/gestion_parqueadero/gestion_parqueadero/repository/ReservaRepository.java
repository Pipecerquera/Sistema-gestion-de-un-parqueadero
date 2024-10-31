package com.gestion_parqueadero.gestion_parqueadero.repository;

import com.gestion_parqueadero.gestion_parqueadero.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}

