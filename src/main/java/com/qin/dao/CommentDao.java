package com.qin.dao;

import com.qin.entity.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> getRank1CommentByBlogIdDesc(Integer blogId);

    List<Comment> getRank2CommentByCIdDesc(Integer commentId);

    int insert(Comment comment);

    Comment getCommentById(Integer id);
}
