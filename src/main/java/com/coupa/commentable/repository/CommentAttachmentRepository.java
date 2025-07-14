package com.coupa.commentable.repository;

import com.coupa.commentable.model.CommentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentAttachmentRepository extends JpaRepository<CommentAttachment, Long> {
} 