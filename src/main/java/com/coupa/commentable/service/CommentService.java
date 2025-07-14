package com.coupa.commentable.service;

import com.coupa.commentable.model.Comment;
import com.coupa.commentable.model.CommentAttachment;
import com.coupa.commentable.model.CommentMention;
import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    List<Comment> getComments(Long commentableId, String commentableType);
    List<Comment> getUnreadComments(Long userId);
    void markAsRead(Long commentId, Long userId);
    void addAttachment(Long commentId, CommentAttachment attachment);
    void processMentions(Comment comment, List<CommentMention> mentions);
} 