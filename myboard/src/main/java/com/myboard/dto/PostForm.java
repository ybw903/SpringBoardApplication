package com.myboard.dto;

import com.myboard.domain.Posts;
import com.myboard.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PostForm {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public PostForm(String title, String content) {
        this.title=title;
        this.content=content;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .build();
    }
}
