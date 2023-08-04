package com.example.forest.service;

public class DuplicateLoginIdException extends Exception {
    public DuplicateLoginIdException(String message) {
        super(message);
    }
}
