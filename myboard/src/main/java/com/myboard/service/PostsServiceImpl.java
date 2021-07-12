package com.myboard.service;

import com.myboard.domain.Posts;
import com.myboard.domain.PostsRepository;
import com.myboard.dto.PostSaveRequestDto;
import com.myboard.exception.PostsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostsServiceImpl implements PostsService{

    static final boolean DELETE_SUCCESS = true;
    static final boolean DELETE_FAILED = false;

    private final PostsRepository postsRepository;

    public PostsServiceImpl(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @Override
    public Posts add(PostSaveRequestDto form) {
        return postsRepository.save(form.toEntity());
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
    public Posts update(Long id, PostSaveRequestDto form) {
        Optional<Posts> optionalPosts = postsRepository.findById(id);
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new PostsNotFoundException("update: Posts not found by :" + id));
        posts.updatePosts(form);
        return postsRepository.save(posts);
    }

    @Override
    public boolean delete(Long id) {
        if(!postsRepository.findById(id).isPresent())
            throw new PostsNotFoundException("delete: Posts not found by : " + id);
        postsRepository.deleteById(id);
        if(!postsRepository.findById(id).isPresent())
            return DELETE_SUCCESS;
        else
            return DELETE_FAILED;
    }
}
