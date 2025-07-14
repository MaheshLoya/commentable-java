package com.coupa.commentable.aspect;

import com.coupa.commentable.aspect.CommentableAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import org.aspectj.lang.Signature;

class CommentableAspectTest {
    @Mock
    private JoinPoint joinPoint;
    @Mock
    private Signature signature;
    private CommentableAspect aspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aspect = new CommentableAspect();
    }

    @Test
    void testBeforeAssignInterceptsAssignMethods() {
        // Simulate a method named assignUser
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("assignUser");
        aspect.beforeAssign(joinPoint);
        // No exception means the advice ran; you can add more verifications if beforeAssign has side effects
    }
} 