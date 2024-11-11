package com.AuthNode.auth.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.AuthNode.auth.Entity.CommentEntity;
import com.AuthNode.auth.IRepository.ICommentRepository;
import com.AuthNode.auth.IService.ICommentService;

@Service
public class CommentService implements ICommentService {

    @Autowired
    public ICommentRepository commentRepository;

    @Override
    public List<CommentEntity> getAll() throws Exception {
        return commentRepository.findAll();
    }

    @Override
    public Page<CommentEntity> getAllPageable(Pageable pageable) throws Exception {
        return commentRepository.findAll(pageable);
    }

    @Override
    public CommentEntity findById(int id) throws Exception {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public CommentEntity save(CommentEntity reserva) {    
        return commentRepository.save(reserva);
    }

    @Override
    public void delete(int id) throws Exception {
    	commentRepository.deleteById(id);
    }

    @Override
    public void update(int id, CommentEntity reserva) throws Exception {
        CommentEntity existingReserva = commentRepository.findById(id)
            .orElseThrow(() -> new Exception("No se encontró el comentario x|con el ID proporcionado"));

        existingReserva.setCommentary(reserva.getCommentary());
        existingReserva.setPersona(reserva.getPersona());
        commentRepository.save(existingReserva);
    }
}
