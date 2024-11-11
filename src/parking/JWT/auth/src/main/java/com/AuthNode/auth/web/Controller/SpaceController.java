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

import com.AuthNode.auth.Entity.SpaceEntity;
import com.AuthNode.auth.IService.ISpaceService;

@CrossOrigin(origins = "*")
@RestController    
@RequestMapping("/api/space")
public class SpaceController {

    @Autowired
    private ISpaceService spaceService;

    private static final Logger logger = LoggerFactory.getLogger(SpaceController.class);

    // Obtener todos los espacios
    @GetMapping("/all")
    public List<SpaceEntity> getAllSpaces() throws Exception {
        return spaceService.getAll();
    }

    // Obtener todos los espacios con paginación
    @GetMapping("/all/pageable")
    public Page<SpaceEntity> getAllSpacesPageable(Pageable pageable) throws Exception {
        return spaceService.getAllPageable(pageable);
    }

    // Obtener espacio por ID
    @GetMapping("/{id}")
    public SpaceEntity getSpaceById(@PathVariable int id) throws Exception {
        return spaceService.findById(id);
    }

    // Agregar un nuevo espacio
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addSpace(@RequestBody SpaceEntity space) {
        Map<String, String> response = new HashMap<>();
        try {
            spaceService.save(space);
            response.put("message", "Espacio añadido con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al agregar el espacio: ", e);
            response.put("error", "Error al agregar el espacio");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Actualizar un espacio existente
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSpace(@PathVariable int id, @RequestBody SpaceEntity space) {
        try {
            spaceService.update(id, space);
            return new ResponseEntity<>("Espacio de parqueo actualizado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Espacio de parqueo no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar solo el campo isOccupied
    @PatchMapping("/update/occupied/{id}")
    public ResponseEntity<String> updateIsOccupied(@PathVariable int id, @RequestBody Map<String, Boolean> request) {
        try {
            boolean isOccupied = request.get("isOccupied");
            spaceService.updateIsOccupied(id, isOccupied);
            return new ResponseEntity<>("Estado de ocupación actualizado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Espacio de parqueo no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar solo el campo isReserved
    @PatchMapping("/update/reserved/{id}")
    public ResponseEntity<String> updateIsReserved(@PathVariable int id, @RequestBody Map<String, Boolean> request) {
        try {
            boolean isReserved = request.get("isReserved");
            spaceService.updateIsReserved(id, isReserved);
            return new ResponseEntity<>("Estado de reserva actualizado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Espacio de parqueo no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un espacio
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteSpace(@PathVariable Integer id) {
        Map<String, String> response = new HashMap<>();
        try {
            spaceService.delete(id);
            response.put("message", "Espacio eliminado con éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error al eliminar el espacio: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
