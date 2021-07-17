package com.myboard.service;

import com.myboard.domain.Posts;
import com.myboard.domain.PostsRepository;
import com.myboard.domain.User;
import com.myboard.domain.UserRepository;
import com.myboard.dto.PostForm;
import com.myboard.dto.PostUpdateForm;
import com.myboard.exception.PostsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService{

    static final boolean DELETE_SUCCESS = true;
    static final boolean DELETE_FAILED = false;

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Posts add(PostForm form, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("사용자 이메일을 찾을 수 없습니다."));
        Posts posts = Posts.builder()
                        .title(form.getTitle())
                        .content(form.getContent())
                        .author(user)
                        .build();
        return postsRepository.save(posts);
    }

    @Override
    @Transactional(readOnly = true)
    public Posts findPost(Long id) {
        return postsRepository.findById(id).orElseThrow(
                () -> new PostsNotFoundException("delete: Posts not found by : " + id));
    }

    @Override
    @Transactional
    public Posts read(Long id) {
        Posts post = postsRepository.findById(id).orElseThrow(
                () -> new PostsNotFoundException("delete: Posts not found by : " + id));
        post.increaseViewCount();
        return postsRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Posts> getPosts(Pageable pageable) {
        int page = (pageable.getPageNumber()==0?0:(pageable.getPageNumber())-1);
        pageable = PageRequest.of(page, 10);
        return postsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Posts> getPostsWithTitle(String title,Pageable pageable) {
        int page = (pageable.getPageNumber()==0?0:(pageable.getPageNumber())-1);
        pageable = PageRequest.of(page, 10);
        return postsRepository.findByTitleContaining(title,pageable);
    }

    @Override
    @Transactional
    public Posts update(Long id, PostUpdateForm postUpdateForm) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new PostsNotFoundException("update: Posts not found by :" + id));
        posts.updatePosts(postUpdateForm);
        return postsRepository.save(posts);
    }

    @Override
    @Transactional
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
