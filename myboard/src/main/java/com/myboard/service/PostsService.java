package com.myboard.service;

import com.myboard.domain.Posts;

import java.util.List;

public interface PostsService {
    Posts add(Posts posts);
    Posts get(Long id);
    List<Posts> getAll();
    Posts update(Posts posts);
    boolean delete(Long id);
}
