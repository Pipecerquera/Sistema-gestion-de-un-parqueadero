package com.gestion_parqueadero.gestion_parqueadero.service;

import com.gestion_parqueadero.gestion_parqueadero.model.Vehiculo;
import com.gestion_parqueadero.gestion_parqueadero.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> obtenerTodos() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }
}
