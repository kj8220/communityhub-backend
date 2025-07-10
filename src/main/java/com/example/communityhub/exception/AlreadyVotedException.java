package com.example.communityhub.exception;

public class AlreadyVotedException extends RuntimeException {
    public AlreadyVotedException(String message) {
        super(message);
    }
}
