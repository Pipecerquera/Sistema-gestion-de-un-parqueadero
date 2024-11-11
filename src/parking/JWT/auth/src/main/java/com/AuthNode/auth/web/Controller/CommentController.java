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

import com.AuthNode.auth.Entity.CommentEntity;
import com.AuthNode.auth.IService.ICommentService;

@CrossOrigin(origins = "*")
@RestController    
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);


    
    @GetMapping("/all")
    public List<CommentEntity> getAllComments() throws Exception {
        return commentService.getAll();
    }

    // Obtener todas las reservas con paginación
    @GetMapping("/all/pageable")
    public ResponseEntity<Map<String, Object>> getAllReservasPageable(Pageable pageable) {
        Map<String, Object> response = new HashMap<>();
        try {
            Page<CommentEntity> reservas = commentService.getAllPageable(pageable);
            response.put("status", "success");
            response.put("data", reservas);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Hubo un problema al obtener los comentarios paginados: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener una reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getReservaById(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            CommentEntity reserva = commentService.findById(id);
            response.put("status", "success");
            response.put("data", reserva);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Reserva no encontrada: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Agregar una nueva reserva
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addReserva(@RequestBody CommentEntity reserva) {
        try {
            commentService.save(reserva);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Comentario agregado con éxito");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Hubo un problema al agregar el comentario: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar una reserva existente
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateReserva(@PathVariable int id, @RequestBody CommentEntity reserva) {
        Map<String, String> response = new HashMap<>();
        try {
            commentService.update(id, reserva);
            response.put("status", "success");
            response.put("message", "Reserva actualizada con éxito");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Reserva no encontrada: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una reserva por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteReserva(@PathVariable("id") int id) {
        Map<String, String> response = new HashMap<>();
        try {
            commentService.delete(id);
            response.put("status", "success");
            response.put("message", "Reserva eliminada con éxito");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Reserva no encontrada: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
