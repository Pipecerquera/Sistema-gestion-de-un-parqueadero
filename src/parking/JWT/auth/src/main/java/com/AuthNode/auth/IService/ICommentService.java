package com.AuthNode.auth.IService;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.AuthNode.auth.Entity.CommentEntity;
public interface ICommentService {
    List<CommentEntity> getAll() throws Exception;
    Page<CommentEntity> getAllPageable(Pageable pageable) throws Exception;
    CommentEntity findById(int id) throws Exception;
    CommentEntity save(CommentEntity comment) throws Exception;
    void update(int id, CommentEntity comment) throws Exception;
    void delete(int id) throws Exception;
}
