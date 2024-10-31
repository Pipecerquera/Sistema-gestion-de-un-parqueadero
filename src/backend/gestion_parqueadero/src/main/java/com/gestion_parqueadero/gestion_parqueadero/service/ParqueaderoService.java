package com.gestion_parqueadero.gestion_parqueadero.service;

import com.gestion_parqueadero.gestion_parqueadero.model.Parqueadero;
import com.gestion_parqueadero.gestion_parqueadero.repository.ParqueaderoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParqueaderoService {

    @Autowired
    private ParqueaderoRepository parqueaderoRepository;

    public List<Parqueadero> obtenerTodos() {
        return parqueaderoRepository.findAll();
    }

    public Parqueadero crearParqueadero(Parqueadero parqueadero) {
        return parqueaderoRepository.save(parqueadero);
    }

    public void eliminarParqueadero(Long id) {
        parqueaderoRepository.deleteById(id);
    }
}



