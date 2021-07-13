package com.myboard.service;

import com.myboard.domain.Comment;
import com.myboard.domain.Posts;
import com.myboard.dto.CommentSaveForm;
import com.myboard.dto.CommentUpdateForm;

import java.util.List;

public interface CommentService {
    void createComment(CommentSaveForm commentSaveForm, Long postsId, String email);
    Comment get(Long id);
    List<Comment> getAll();
    Comment update(Long id, CommentUpdateForm commentUpdateForm);
    boolean delete(Long id);
}
