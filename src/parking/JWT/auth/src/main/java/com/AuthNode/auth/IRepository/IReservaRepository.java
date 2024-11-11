package com.AuthNode.auth.IRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.AuthNode.auth.Entity.ReservaEntity;

public interface IReservaRepository extends JpaRepository<ReservaEntity, Integer>{

	
}
