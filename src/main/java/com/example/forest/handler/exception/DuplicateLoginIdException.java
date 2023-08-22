package com.example.forest.handler.exception;

public class DuplicateLoginIdException extends Exception {
    public DuplicateLoginIdException(String message) {
        super(message);
    }
}
