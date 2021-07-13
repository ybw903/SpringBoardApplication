package com.myboard.dto;

import com.myboard.domain.Comment;
import lombok.Getter;

@Getter
public class CommentCreateDto {
    private String content;
    private Comment comment;

    public CommentCreateDto(String content, Comment comment) {
        this.content = content;
        this.comment = comment;
    }
}
