package com.coupa.commentable.aspect;


import com.coupa.commentable.interfaces.AssignmentCommentable;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommentableAspect {
    private static final Logger logger = LoggerFactory.getLogger(CommentableAspect.class);

    @Before("execution(* com.coupa.commentable.interfaces.AssignmentCommentable+.assign*(..)) && target(commentable)")
    public void beforeAssignment(AssignmentCommentable commentable) {
        logger.debug("Intercepting assignment for commentable: {}", commentable.getId());
        Object[] args = new Object[0]; // Simplified; extract args as needed
        AssignmentCommentable.AssignmentOptions options = new AssignmentCommentable.AssignmentOptions("USER", 0L, "ROLE"); // Placeholder
        commentable.beforeAssign(null, options);
    }
}