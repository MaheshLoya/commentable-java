package com.coupa.commentable.repository;

import com.coupa.commentable.model.CommentReadReceipt;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReadReceiptRepository extends JpaRepository<CommentReadReceipt, Long> {
    Optional<CommentReadReceipt> findByCommentIdAndUserId(Long commentId, Long userId);
    boolean existsByCommentIdAndUserId(Long commentId, Long userId);
} 