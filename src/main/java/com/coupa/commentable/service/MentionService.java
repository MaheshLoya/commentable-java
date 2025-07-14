package com.coupa.commentable.service;

import com.coupa.commentable.model.CommentMention;

import java.util.List;

public interface MentionService {
    List<String> extractMentions(String content);
    List<CommentMention> createMentions(Long commentId, List<String> mentionees);
    void notifyMentionees(List<CommentMention> mentions);
    void grantAccessToMentionees(List<CommentMention> mentions);
}