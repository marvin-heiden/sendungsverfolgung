package com.senacor.tecco.MessageReadService.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Step {
    @Getter
    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date timestamp;
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private String status;
}
