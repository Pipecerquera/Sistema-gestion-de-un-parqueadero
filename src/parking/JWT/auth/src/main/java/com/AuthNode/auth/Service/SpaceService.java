package com.AuthNode.auth.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.AuthNode.auth.Entity.SpaceEntity;
import com.AuthNode.auth.IRepository.ISpaceRepository;
import com.AuthNode.auth.IService.ISpaceService;

@Service
public class SpaceService implements ISpaceService {

    @Autowired
    public ISpaceRepository spaceRepository;

    @Override
    public List<SpaceEntity> getAll() throws Exception {
        return spaceRepository.findAll();
    }

    @Override
    public Page<SpaceEntity> getAllPageable(Pageable pageable) throws Exception {
        return spaceRepository.findAll(pageable);
    }

    @Override
    public SpaceEntity findById(int id) throws Exception {
        SpaceEntity space = spaceRepository.findById(id).orElse(null);
        return space;
    }

    @Override
    public SpaceEntity save(SpaceEntity space) {    
        return spaceRepository.save(space);
    }

    @Override
    public void delete(int id) throws Exception {
        spaceRepository.deleteById(id);
    }

    @Override
    public void update(int id, SpaceEntity space) throws Exception {
        SpaceEntity existingSpace = spaceRepository.findById(id)
            .orElseThrow(() -> new Exception("No se encontró el espacio de parqueo con el ID proporcionado"));

        existingSpace.setOccupied(space.isOccupied());
        existingSpace.setRelativeName(space.getRelativeName());
        existingSpace.setTipoVehiculo(space.getTipoVehiculo());
        existingSpace.setReserved(space.isReserved());
        spaceRepository.save(existingSpace);
    }

    // Método para actualizar solo el campo isOccupied
    public SpaceEntity updateIsOccupied(int id, boolean isOccupied) throws Exception {
        SpaceEntity existingSpace = spaceRepository.findById(id)
            .orElseThrow(() -> new Exception("No se encontró el espacio de parqueo con el ID proporcionado"));

        existingSpace.setOccupied(isOccupied);
        return spaceRepository.save(existingSpace);
    }

    // Método para actualizar solo el campo isReserved
    public SpaceEntity updateIsReserved(int id, boolean isReserved) throws Exception {
        SpaceEntity existingSpace = spaceRepository.findById(id)
            .orElseThrow(() -> new Exception("No se encontró el espacio de parqueo con el ID proporcionado"));

        existingSpace.setReserved(isReserved);
        return spaceRepository.save(existingSpace);
    }
}
