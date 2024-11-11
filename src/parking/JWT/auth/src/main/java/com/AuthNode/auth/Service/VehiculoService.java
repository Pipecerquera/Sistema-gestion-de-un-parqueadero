package com.AuthNode.auth.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.AuthNode.auth.Entity.VehiculoEntity;
import com.AuthNode.auth.IRepository.IVehiculoRepository;
import com.AuthNode.auth.IService.IVehiculoService;

@Service
public class VehiculoService implements IVehiculoService {

    @Autowired
    public IVehiculoRepository vehiculoRepository;

    @Override
    public List<Object[]> getAll() throws Exception {
        return vehiculoRepository.findAllVehiculosWithPersonaFullname();
    }

    @Override
    public Page<VehiculoEntity> getAllPageable(Pageable pageable) throws Exception {
        return vehiculoRepository.findAll(pageable);
    }

    @Override
    public VehiculoEntity findById(int id) throws Exception {
        return vehiculoRepository.findById(id).orElse(null);
    }

    @Override
    public VehiculoEntity save(VehiculoEntity login) {
        return vehiculoRepository.save(login);
    }

    @Override
    public void delete(int id) throws Exception {
        vehiculoRepository.deleteById(id);
    }

    @Override
    public void update(int id, VehiculoEntity login) throws Exception {
        VehiculoEntity vehiculo = vehiculoRepository.findById(id)
            .orElseThrow(() -> new Exception("No se encontró el vehículo con el ID proporcionado"));

        vehiculo.setPlaca(login.getPlaca());
        vehiculo.setTipoVehiculo(login.getTipoVehiculo());
        vehiculo.setPersona(login.getPersona());
        vehiculoRepository.save(vehiculo);
    }

    // Implementación del nuevo método para buscar vehículos por el ID de la persona
    @Override
    public List<Object[]> findVehiculosByPersonaId(Integer personaId) throws Exception {
        return vehiculoRepository.findVehiculosByPersonaId(personaId);
    }
}
