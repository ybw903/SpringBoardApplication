package com.myboard.service;

import com.myboard.domain.Posts;
import com.myboard.dto.PostForm;
import com.myboard.dto.PostUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostsService {
    Posts add(PostForm form, String email);
    Posts findPost(Long id);
    Posts read(Long id);
    Page<Posts> getPosts(Pageable pageable);
    Posts update(Long id, PostUpdateForm postUpdateForm);
    boolean delete(Long id);
}
