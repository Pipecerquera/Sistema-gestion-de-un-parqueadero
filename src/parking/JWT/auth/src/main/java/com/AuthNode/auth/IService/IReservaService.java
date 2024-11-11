package com.AuthNode.auth.IService;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.AuthNode.auth.Entity.ReservaEntity;

public interface IReservaService {
    List<ReservaEntity> getAll() throws Exception;
    Page<ReservaEntity> getAllPageable(Pageable pageable) throws Exception;
    ReservaEntity findById(int id) throws Exception;
    ReservaEntity save(ReservaEntity reserva) throws Exception;
    void update(int id, ReservaEntity reserva) throws Exception;
    void delete(int id) throws Exception;
}
