package com.coupa.commentable.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment_attachments")
@Getter
@Setter
@NoArgsConstructor
public class CommentAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String contentType;

    // Note: File storage is handled by an external module (e.g., Paperclip-like library).
    // This entity stores metadata and links to the file, assuming an adapter pattern
    // for integration with cloud or local storage.
}