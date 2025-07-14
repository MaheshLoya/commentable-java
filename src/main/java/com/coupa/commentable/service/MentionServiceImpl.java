package com.coupa.commentable.service;


import com.coupa.commentable.exception.CommentException;
import com.coupa.commentable.model.Comment;
import com.coupa.commentable.model.CommentMention;
import com.coupa.commentable.repository.CommentMentionRepository;
import com.coupa.commentable.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MentionServiceImpl implements MentionService {
    private static final Logger logger = LoggerFactory.getLogger(MentionServiceImpl.class);
    private static final Pattern MENTION_PATTERN = Pattern.compile("@[\\w-]+");

    @Autowired
    private CommentMentionRepository mentionRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<String> extractMentions(String content) {
        Matcher matcher = MENTION_PATTERN.matcher(content);
        return matcher.results()
            .map(matchResult -> matchResult.group().substring(1)) // Remove '@'
            .collect(Collectors.toList());
    }

    @Override
    public List<CommentMention> createMentions(Long commentId, List<String> mentionees) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty()) {
            throw new CommentException("Comment not found: " + commentId);
        }
        Comment comment = commentOpt.get();
        List<CommentMention> mentions = mentionees.stream().map(mentionee -> {
            CommentMention mention = new CommentMention();
            mention.setComment(comment);
            mention.setMentioneeId(parseMentioneeId(mentionee)); // Assume mentionee is numeric ID
            mention.setMentioneeType("USER"); // Placeholder; customize as needed
            return mention;
        }).collect(Collectors.toList());
        return mentionRepository.saveAll(mentions);
    }

    @Override
    public void notifyMentionees(List<CommentMention> mentions) {
        // Placeholder: Integrate with external notification service (e.g., email, push notifications)
        mentions.forEach(mention ->
            logger.info("Notifying mentionee: {} for comment: {}", mention.getMentioneeId(), mention.getComment().getId())
        );
    }

    @Override
    public void grantAccessToMentionees(List<CommentMention> mentions) {
        // Placeholder: Implement access control logic based on application’s security model
        mentions.forEach(mention ->
            logger.info("Granting access to mentionee: {} for comment: {}", mention.getMentioneeId(), mention.getComment().getId())
        );
    }

    private Long parseMentioneeId(String mentionee) {
        try {
            return Long.parseLong(mentionee);
        } catch (NumberFormatException e) {
            throw new CommentException("Invalid mentionee format: " + mentionee);
        }
    }
}