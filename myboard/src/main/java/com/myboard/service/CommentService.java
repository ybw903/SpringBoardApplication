package com.myboard.service;

import com.myboard.domain.Comment;
import com.myboard.domain.Posts;

import java.util.List;

public interface CommentService {
    Comment add(Comment comment);
    Comment get(Long id);
    List<Comment> getAll();
    Comment update(Comment comment);
    boolean delete(Long id);
}
