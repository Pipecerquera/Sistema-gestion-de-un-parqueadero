package com.AuthNode.auth.IRepository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.AuthNode.auth.Entity.VehiculoEntity;

public interface IVehiculoRepository extends JpaRepository<VehiculoEntity, Integer>{

	 @Query("SELECT v.id AS id, v.tipoVehiculo AS tipoVehiculo, v.placa AS placa, v.persona.fullname AS fullname " +
	           "FROM VehiculoEntity v JOIN v.persona p")
	    List<Object[]> findAllVehiculosWithPersonaFullname();
	    

	    @Query("SELECT v.id AS id, v.tipoVehiculo AS tipoVehiculo, v.placa AS placa, v.persona.fullname AS fullname " +
	           "FROM VehiculoEntity v JOIN v.persona p WHERE p.id = :personaId")
	    List<Object[]> findVehiculosByPersonaId(Integer personaId);

}
