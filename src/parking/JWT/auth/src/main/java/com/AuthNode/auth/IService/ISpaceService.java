package com.AuthNode.auth.IService;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.AuthNode.auth.Entity.SpaceEntity;

public interface ISpaceService {
    List<SpaceEntity> getAll() throws Exception;
    Page<SpaceEntity> getAllPageable(Pageable pageable) throws Exception;
    SpaceEntity findById(int id) throws Exception;
    SpaceEntity save(SpaceEntity space) throws Exception;
    void update(int id, SpaceEntity space) throws Exception;
    void delete(int id) throws Exception;
	SpaceEntity updateIsOccupied(int id, boolean isOccupied) throws Exception;
	SpaceEntity updateIsReserved(int id, boolean isReserved) throws Exception;
}
