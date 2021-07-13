package com.myboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class CommentGoneException extends RuntimeException{
    public CommentGoneException(String message) {
        super(message);
    }
}
