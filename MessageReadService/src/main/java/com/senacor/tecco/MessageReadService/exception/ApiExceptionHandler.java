package com.senacor.tecco.MessageReadService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiNoContentException.class)
    public ResponseEntity<?> handleApiNoContentException(ApiNoContentException e){

        HttpStatus noContent = HttpStatus.NO_CONTENT;

        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                noContent,
                new Date()
        );

        return ResponseEntity.status(noContent).body(apiException);
    }
}
