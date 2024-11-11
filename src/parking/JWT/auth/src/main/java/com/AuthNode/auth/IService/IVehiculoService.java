package com.AuthNode.auth.IService;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.AuthNode.auth.Entity.VehiculoEntity;

public interface IVehiculoService {
    List<Object[]> getAll() throws Exception;
    Page<VehiculoEntity> getAllPageable(Pageable pageable) throws Exception;
    VehiculoEntity findById(int id) throws Exception;
    VehiculoEntity save(VehiculoEntity vehiculo) throws Exception;
    void update(int id, VehiculoEntity vehiculo) throws Exception;
    void delete(int id) throws Exception;
    List<Object[]> findVehiculosByPersonaId(Integer personaId) throws Exception;
}
