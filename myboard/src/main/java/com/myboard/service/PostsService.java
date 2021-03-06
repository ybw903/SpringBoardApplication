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
    Page<Posts> getPostsWithTitle( String title,Pageable pageable);
    Posts update(Long id, PostUpdateForm postUpdateForm);
    boolean like(Long postsId, String email);
    boolean delete(Long id);
    public boolean isLike(Long postsId, String email);
}
