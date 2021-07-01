package com.myboard.service;

import com.myboard.domain.Posts;
import com.myboard.domain.PostsRepository;
import com.myboard.exception.PostsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsServiceImpl implements PostsService{

    final boolean DELETE_SUCCESS = true;
    final boolean DELETE_FAILED = false;

    private PostsRepository postsRepository;

    @Autowired
    public PostsServiceImpl(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @Override
    public Posts add(Posts posts) {
        return postsRepository.save(posts);
    }

    @Override
    public Posts get(Long id) {
        return postsRepository.findById(id).orElseThrow(
                () -> new PostsNotFoundException("delete: Posts not found by : " + id));
    }

    @Override
    public List<Posts> getAll() {
        return postsRepository.findAll();
    }

    @Override
    public Posts update(Posts posts) {
        if (postsRepository.findById(posts.getId()).isPresent())
            return postsRepository.save(posts);
        else
            throw new PostsNotFoundException("update: Posts not found by :" + posts.getId());
    }

    @Override
    public boolean delete(Long id) {
        postsRepository.deleteById(id);
        if(!postsRepository.findById(id).isPresent())
            return DELETE_SUCCESS;
        else
            return DELETE_FAILED;
    }
}
