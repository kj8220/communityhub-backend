package com.example.communityhub.exception;

public class DuplicatePostException extends RuntimeException {
    public DuplicatePostException(String message) {
        super(message);
    }
}
