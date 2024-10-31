package com.gestion_parqueadero.gestion_parqueadero.service;

import com.gestion_parqueadero.gestion_parqueadero.model.Reserva;
import com.gestion_parqueadero.gestion_parqueadero.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
}

