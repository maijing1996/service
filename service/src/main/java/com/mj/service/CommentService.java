package com.mj.service;

import com.mj.model.Comment;
import com.querydsl.core.BooleanBuilder;

import java.util.List;

public interface CommentService {

    Comment getOneComment(Integer commentId);

    List getComments(Integer goodsId);

    void addComment(Comment comment);

    void deleteComment(Comment comment);

    Integer getUnreadCount(Integer userId);

    List<Comment> getUnreadComment(Integer userId);

    BooleanBuilder booleanBuilder(Integer userId, String type);

    void commentIsRead(Integer commentId);

    List getUserComment(Integer userId);
}
