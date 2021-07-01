package com.myboard.controller;

import com.myboard.domain.Posts;
import com.myboard.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostsController {

    private final PostsService postsService;

    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("/posts/id")
    public ResponseEntity<PostsDto> findPostsById(@RequestParam Long id) {
        Posts posts = postsService.get(id);
        return ResponseEntity.ok(PostsDto.of(posts));
    }
    @GetMapping("/posts")
    public ResponseEntity<List<PostsDto>> findPostsAll() {
        List<Posts> postsList = postsService.getAll();
        List<PostsDto> postsDtos = postsList.stream().map(PostsDto::of).collect(Collectors.toList());
        return ResponseEntity.ok(postsDtos);
    }

    @PostMapping("/posts")
    public ResponseEntity<PostsDto> addPosts(@RequestBody PostsDto postsDto) {
        Posts posts = postsService.add(Posts.builder()
                                        .title(postsDto.getTitle())
                                        .content(postsDto.getContent())
                                        .author(postsDto.getAuthor())
                                        .build());
        return ResponseEntity.ok(PostsDto.of(posts));
    }

}
