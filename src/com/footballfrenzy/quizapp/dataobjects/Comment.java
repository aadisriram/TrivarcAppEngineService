package com.footballfrenzy.quizapp.dataobjects;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Comment {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long commentId;

  @Persistent
  private String comment;

  @Persistent
  private Date commentDate;

  public Comment(String comment, String comment, Date commentDate) {
    super();
    this.comment = comment;
    this.commentDate = commentDate;
  }

  public Long getCommentId() {
    return commentId;
  }

  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Date getCommentDate() {
    return commentDate;
  }

  public void setCommentDate(Date commentDate) {
    this.commentDate = commentDate;
  }
}
