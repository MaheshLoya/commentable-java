package com.coupa.commentable.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_read_receipts")
@Getter
@Setter
@NoArgsConstructor
public class CommentReadReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column(nullable = false)
    private Long userId;

    @Column
    private LocalDateTime readAt;

    @Column(nullable = false)
    private Long commentableId;

    @Column(nullable = false)
    private String commentableType;
}