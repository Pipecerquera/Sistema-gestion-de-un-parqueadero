package com.AuthNode.auth.IRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.AuthNode.auth.Entity.SpaceEntity;

public interface ISpaceRepository extends JpaRepository<SpaceEntity, Integer>{

	
}
