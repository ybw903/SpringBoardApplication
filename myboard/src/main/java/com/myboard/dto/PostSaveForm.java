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
public class PostSaveForm {

    private String title;
    private String content;
    private User author;

    @Builder
    public PostSaveForm(String title, String content, User author) {
        this.title=title;
        this.content=content;
        this.author=author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
