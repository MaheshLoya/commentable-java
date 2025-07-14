package com.coupa.commentable.service;

import com.coupa.commentable.exception.CommentException;
import com.coupa.commentable.model.Comment;
import com.coupa.commentable.model.CommentAttachment;
import com.coupa.commentable.model.CommentMention;
import com.coupa.commentable.model.CommentReadReceipt;
import com.coupa.commentable.repository.CommentRepository;
import com.coupa.commentable.repository.CommentAttachmentRepository;
import com.coupa.commentable.repository.CommentMentionRepository;
import com.coupa.commentable.repository.CommentReadReceiptRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentAttachmentRepository commentAttachmentRepository;
    @Autowired
    private CommentMentionRepository commentMentionRepository;
    @Autowired
    private CommentReadReceiptRepository commentReadReceiptRepository;
    @Autowired
    private MentionService mentionService;

    @Override
    public Comment createComment(Comment comment) {
        try {
            Comment saved = commentRepository.save(comment);
            processMentions(saved, comment.getMentions());
            for (CommentAttachment attachment : comment.getAttachments()) {
                addAttachment(saved.getId(), attachment);
            }
            return saved;
        } catch (Exception e) {
            logger.error("Error creating comment", e);
            throw new CommentException("Failed to create comment", e);
        }
    }

    @Override
    public List<Comment> getComments(Long commentableId, String commentableType) {
        try {
            return commentRepository.findByCommentableIdAndCommentableType(commentableId, commentableType);
        } catch (Exception e) {
            logger.error("Error fetching comments", e);
            throw new CommentException("Failed to fetch comments", e);
        }
    }

    @Override
    public List<Comment> getUnreadComments(Long userId) {
        try {
            List<Comment> allComments = commentRepository.findAll();
            return allComments.stream()
                .filter(comment -> comment.getReadReceipts().stream().noneMatch(rr -> rr.getUserId().equals(userId)))
                .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching unread comments", e);
            throw new CommentException("Failed to fetch unread comments", e);
        }
    }

    @Override
    public void markAsRead(Long commentId, Long userId) {
        try {
            Optional<CommentReadReceipt> existing = commentReadReceiptRepository.findByCommentIdAndUserId(commentId, userId);
            if (existing.isEmpty()) {
                CommentReadReceipt receipt = new CommentReadReceipt();
                receipt.setCommentId(commentId);
                receipt.setUserId(userId);
                commentReadReceiptRepository.save(receipt);
            }
        } catch (Exception e) {
            logger.error("Error marking comment as read", e);
            throw new CommentException("Failed to mark comment as read", e);
        }
    }

    @Override
    public void addAttachment(Long commentId, CommentAttachment attachment) {
        try {
            // Placeholder for actual storage logic
            attachment.setCommentId(commentId);
            commentAttachmentRepository.save(attachment);
        } catch (Exception e) {
            logger.error("Error adding attachment", e);
            throw new CommentException("Failed to add attachment", e);
        }
    }

    @Override
    public void processMentions(Comment comment, List<CommentMention> mentions) {
        try {
            mentionService.processMentions(comment, mentions);
        } catch (Exception e) {
            logger.error("Error processing mentions", e);
            throw new CommentException("Failed to process mentions", e);
        }
    }
} 