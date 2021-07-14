package com.myboard.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class CommentSaveForm {
    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;
    private String commentId;
}
