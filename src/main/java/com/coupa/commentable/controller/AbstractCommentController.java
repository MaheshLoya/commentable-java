package com.coupa.commentable.controller;


import com.coupa.commentable.model.Comment;
import com.coupa.commentable.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Abstract base controller for comment operations.
 * Applications should extend this class and add appropriate
 * request mappings and security configurations.
 */
public abstract class AbstractCommentController {

    @Autowired
    protected CommentService commentService;

    /**
     * Get comments for a commentable entity.
     */
    protected ResponseEntity<List<Comment>> getComments(Long commentableId, String commentableType) {
        List<Comment> comments = commentService.getComments(commentableId, commentableType);
        return ResponseEntity.ok(comments);
    }

    /**
     * Create a new comment.
     */
    protected ResponseEntity<Comment> createComment(
            Long commentableId, String commentableType, String content, Long userId, Boolean toSupplier) {

        Comment comment = commentService.createComment(
                commentableId, commentableType, content, userId, toSupplier != null ? toSupplier : false);

        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    /**
     * Get unread comments.
     */
    protected ResponseEntity<List<Comment>> getUnreadComments(
            Long commentableId, String commentableType, Long userId) {

        List<Comment> unreadComments = commentService.getUnreadComments(
                commentableId, commentableType, userId);

        return ResponseEntity.ok(unreadComments);
    }

    // Other common methods...
}