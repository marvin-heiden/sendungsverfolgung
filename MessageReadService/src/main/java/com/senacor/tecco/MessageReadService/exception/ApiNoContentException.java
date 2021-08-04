package com.senacor.tecco.MessageReadService.exception;

public class ApiNoContentException extends RuntimeException{
    public ApiNoContentException(String message) {
        super(message);
    }

    public ApiNoContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
