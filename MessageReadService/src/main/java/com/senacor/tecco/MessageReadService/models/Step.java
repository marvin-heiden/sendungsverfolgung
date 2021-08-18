package com.senacor.tecco.MessageReadService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document
@EqualsAndHashCode
public class Step {
    private Instant timestamp;
    private String message;
    private String status;
    private String facility;
}
