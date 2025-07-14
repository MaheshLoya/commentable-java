package com.coupa.commentable.service;

import com.coupa.commentable.exception.CommentException;
import com.coupa.commentable.model.*;
import com.coupa.commentable.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentAttachmentRepository attachmentRepository;
    @Mock
    private CommentMentionRepository mentionRepository;
    @Mock
    private CommentReadReceiptRepository readReceiptRepository;
    @Mock
    private MentionService mentionService;
    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateComment() {
        Comment comment = new Comment();
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        Comment result = commentService.createComment(comment);
        assertNotNull(result);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void testGetComments() {
        List<Comment> comments = List.of(new Comment());
        when(commentRepository.findByCommentableIdAndCommentableType(1L, "Invoice")).thenReturn(comments);
        List<Comment> result = commentService.getComments(1L, "Invoice");
        assertEquals(1, result.size());
    }

    @Test
    void testGetUnreadComments() {
        Comment comment = new Comment();
        comment.setId(1L);
        when(commentRepository.findAll()).thenReturn(List.of(comment));
        when(readReceiptRepository.findByCommentIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        List<Comment> result = commentService.getUnreadComments(1L);
        assertEquals(1, result.size());
    }

    @Test
    void testMarkAsRead() {
        CommentReadReceipt receipt = new CommentReadReceipt();
        when(readReceiptRepository.findByCommentIdAndUserId(1L, 1L)).thenReturn(Optional.empty());
        doReturn(receipt).when(readReceiptRepository).save(any(CommentReadReceipt.class));
        commentService.markAsRead(1L, 1L);
        verify(readReceiptRepository).save(any(CommentReadReceipt.class));
    }

    @Test
    void testAddAttachment() {
        CommentAttachment attachment = new CommentAttachment();
        when(attachmentRepository.save(any(CommentAttachment.class))).thenReturn(attachment);
        commentService.addAttachment(1L, attachment);
        verify(attachmentRepository).save(any(CommentAttachment.class));
    }

    @Test
    void testProcessMentions() {
        Comment comment = new Comment();
        List<CommentMention> mentions = List.of(new CommentMention());
        doNothing().when(mentionService).processMentions(comment, mentions);
        commentService.processMentions(comment, mentions);
        verify(mentionService).processMentions(comment, mentions);
    }
} 