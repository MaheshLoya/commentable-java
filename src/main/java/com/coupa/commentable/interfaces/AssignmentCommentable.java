package com.coupa.commentable.interfaces;

import com.coupa.commentable.model.Comment;

public interface AssignmentCommentable extends Commentable {
  void beforeAssign(Object assignee, AssignmentOptions options);

  void addCommentForAssignment(Comment comment, AssignmentOptions options);

  boolean supportsAssignmentComments();

  class AssignmentOptions {
    private final String assigneeType;
    private final Long assigneeId;
    private final String role;

    public AssignmentOptions(String assigneeType, Long assigneeId, String role) {
      this.assigneeType = assigneeType;
      this.assigneeId = assigneeId;
      this.role = role;
    }

    public String getAssigneeType() {
      return assigneeType;
    }

    public Long getAssigneeId() {
      return assigneeId;
    }

    public String getRole() {
      return role;
    }
  }
}
