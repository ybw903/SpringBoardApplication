package com.myboard.exception;

public class DuplicateUserEmailException extends RuntimeException{
    public DuplicateUserEmailException(String message) {
        super(message);
    }

    public DuplicateUserEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
