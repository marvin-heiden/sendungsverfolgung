package com.senacor.tecco.MessageReadService.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor
@Data
public class ApiException {
    private final String message;
    private final HttpStatus status;
    private final Date timestamp;
}
