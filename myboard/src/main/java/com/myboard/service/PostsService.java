package com.myboard.service;

import com.myboard.domain.Posts;
import com.myboard.dto.PostSaveRequestDto;

import java.util.List;

public interface PostsService {
    Posts add(PostSaveRequestDto form);
    Posts get(Long id);
    List<Posts> getAll();
    Posts update(Long id, PostSaveRequestDto form);
    boolean delete(Long id);
}
