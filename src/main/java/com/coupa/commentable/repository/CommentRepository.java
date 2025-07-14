package com.coupa.commentable.repository;

import com.coupa.commentable.model.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCommentableIdAndCommentableType(Long commentableId, String commentableType);
    List<Comment> findByCommentableIdAndCommentableTypeAndToSupplier(Long commentableId, String commentableType, Boolean toSupplier);
} 