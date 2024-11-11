package com.AuthNode.auth.web.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.AuthNode.auth.Entity.VehiculoEntity;
import com.AuthNode.auth.IService.IVehiculoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/vehiculo")
public class VehiculoController {

    @Autowired
    private IVehiculoService vehiculoService;

    private static final Logger logger = LoggerFactory.getLogger(VehiculoController.class);

    @GetMapping("/all")
    public List<Object[]> getAllVehiculos() throws Exception {
        return vehiculoService.getAll();
    }

    @GetMapping("/all/pageable")
    public Page<VehiculoEntity> getAllVehiculosPageable(Pageable pageable) throws Exception {
        return vehiculoService.getAllPageable(pageable);
    }

    @GetMapping("/{id}")
    public VehiculoEntity getVehiculoById(@PathVariable int id) throws Exception {
        return vehiculoService.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addVehiculo(@RequestBody VehiculoEntity vehiculo) throws Exception {
        Map<String, String> response = new HashMap<>();
        
        if (vehiculo.getPlaca() != null && !vehiculo.getPlaca().isEmpty() &&
            vehiculo.getTipoVehiculo() != null && !vehiculo.getTipoVehiculo().isEmpty()) {
            
            vehiculoService.save(vehiculo);
            response.put("message", "Vehículo añadido con éxito");
            return ResponseEntity.ok(response); 
            
        } else {
            if (vehiculo.getPlaca() == null || vehiculo.getPlaca().isEmpty()) {
                response.put("error", "Error: Placa del vehículo requerida");
            } else if (vehiculo.getTipoVehiculo() == null || vehiculo.getTipoVehiculo().isEmpty()) {
                response.put("error", "Error: Tipo de vehículo requerido");
            }
            return ResponseEntity.badRequest().body(response);
        }
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVehiculo(@PathVariable int id, @RequestBody VehiculoEntity vehiculo) {
        try {
            vehiculoService.update(id, vehiculo);
            return new ResponseEntity<>("Vehículo actualizado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Vehículo no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVehiculo(@PathVariable("id") int id) {
        try {
            vehiculoService.delete(id);
            return new ResponseEntity<>("Vehículo eliminado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Vehículo no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Nuevo endpoint para obtener vehículos por el ID de la persona
    @GetMapping("/persona/{personaId}")
    public List<Object[]> getVehiculosByPersonaId(@PathVariable("personaId") Integer personaId) throws Exception {
        return vehiculoService.findVehiculosByPersonaId(personaId);
    }
}
