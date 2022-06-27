package com.tony.blog.exception;

public class BusinessErrorException extends RuntimeException {
    private String code;
    private String message;

    public BusinessErrorException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
