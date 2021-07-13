package com.myboard.dto;

import com.myboard.domain.Posts;
import com.myboard.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostUpdateForm {

    private String title;
    private String content;

    @Builder
    public PostUpdateForm(String title, String content) {
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

