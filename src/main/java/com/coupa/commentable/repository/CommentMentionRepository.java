package com.coupa.commentable.repository;

import com.coupa.commentable.model.CommentMention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentMentionRepository extends JpaRepository<CommentMention, Long> {
} 