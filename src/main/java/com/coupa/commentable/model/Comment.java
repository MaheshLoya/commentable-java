package com.coupa.commentable.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long commentableId;

  @Column(nullable = false)
  private String commentableType;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Long createdById;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime updatedAt;

  private Boolean toSupplier = false;

  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentAttachment> attachments = new ArrayList<>();

  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentMention> mentions = new ArrayList<>();

  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentReadReceipt> readReceipts = new ArrayList<>();

  public void addAttachment(CommentAttachment attachment) {
    attachments.add(attachment);
    attachment.setComment(this);
  }

  public void removeAttachment(CommentAttachment attachment) {
    attachments.remove(attachment);
    attachment.setComment(null);
  }

  public void addMention(CommentMention mention) {
    mentions.add(mention);
    mention.setComment(this);
  }

  public void removeMention(CommentMention mention) {
    mentions.remove(mention);
    mention.setComment(null);
  }

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
