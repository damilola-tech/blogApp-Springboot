package com.blogapp.web.exceptions;

public class PostDoesNotExistsException extends Exception {
    public PostDoesNotExistsException(String message) {
        super(message);
    }
}
