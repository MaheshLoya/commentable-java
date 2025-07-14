package com.coupa.commentable.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.coupa.commentable.aspect.CommentableAspect;
import com.coupa.commentable.service.CommentService;
import com.coupa.commentable.service.CommentServiceImpl;
import com.coupa.commentable.service.MentionService;
import com.coupa.commentable.service.MentionServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommentableAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CommentService commentService() {
        return new CommentServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public MentionService mentionService() {
        return new MentionServiceImpl();
    }

    @Bean
    public CommentableAspect commentableAspect() {
        return new CommentableAspect();
    }
}