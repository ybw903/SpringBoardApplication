package com.myboard.dto;

import com.myboard.domain.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private int viewCount;
    private String author;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    public static PostResponseDto of(Posts posts) {
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(posts.getId());
        postResponseDto.setTitle(posts.getTitle());
        postResponseDto.setContent(posts.getContent());
        postResponseDto.setViewCount(posts.getViewCount());
        postResponseDto.setAuthor(posts.getAuthor().getEmail());
        postResponseDto.setCreatedDateTime(posts.getCreatedDateTime());
        return postResponseDto;
    }
}
