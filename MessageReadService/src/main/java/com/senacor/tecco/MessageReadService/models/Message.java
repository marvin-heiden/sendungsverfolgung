package com.senacor.tecco.MessageReadService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    @Getter
    @Setter
    @JsonProperty("MessageHeader")
    private MessageHeader messageHeader;
    @Getter
    @Setter
    @JsonProperty("Event")
    private Event event;
}
