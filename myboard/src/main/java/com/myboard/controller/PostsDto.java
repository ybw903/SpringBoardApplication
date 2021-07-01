package com.myboard.controller;

import com.myboard.domain.Posts;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostsDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    private PostsDto(Long id, String title,String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public static PostsDto of(Posts posts) {
        return new PostsDto(posts.getId(), posts.getTitle(), posts.getContent(), posts.getAuthor());
    }
}
