package com.myboard.service;

import com.myboard.domain.Posts;
import com.myboard.dto.PostSaveForm;
import com.myboard.dto.PostUpdateForm;

import java.util.List;

public interface PostsService {
    Posts add(PostSaveForm form, String email);
    Posts findPost(Long id);
    Posts read(Long id);
    List<Posts> getAll();
    Posts update(Long id, PostUpdateForm form);
    boolean delete(Long id);
}
