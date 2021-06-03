package com.blogapp.web.exceptions;

public class PostObjectIsNullException extends Throwable {
    public PostObjectIsNullException() {
        super();
    }

    public PostObjectIsNullException(String message) {
        super(message);
    }

    public PostObjectIsNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
