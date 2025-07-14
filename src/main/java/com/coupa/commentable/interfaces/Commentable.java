package com.coupa.commentable.interfaces;

import java.util.List;

public interface Commentable<C> {
  Long getId();

  String getCommentableType();

  List<C> getComments();

  void addComment(C comment);

  List<C> getUnreadComments();

  boolean supportsComments();

  boolean supportsMentions();

  boolean supportsAttachments();

  boolean allowMentioneeAccess();
}
