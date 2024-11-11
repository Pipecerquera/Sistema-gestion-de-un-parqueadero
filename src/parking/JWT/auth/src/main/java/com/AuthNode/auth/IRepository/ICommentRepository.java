package com.AuthNode.auth.IRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.AuthNode.auth.Entity.CommentEntity;

public interface ICommentRepository extends JpaRepository<CommentEntity, Integer>{

	
}
