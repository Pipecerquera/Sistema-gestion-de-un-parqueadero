package com.AuthNode.auth.IRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.AuthNode.auth.Entity.PersonaEntity;

public interface IPersonaRepository extends JpaRepository<PersonaEntity, Integer>{

	Optional<PersonaEntity> findByEmail(String email);
	
}
