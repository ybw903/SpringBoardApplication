package com.myboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentSaveForm {
    private String content;
    private String commentId;
}
