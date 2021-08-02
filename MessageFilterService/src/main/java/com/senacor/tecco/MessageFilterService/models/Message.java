package com.senacor.tecco.MessageFilterService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @JsonProperty("MessageHeader")
    private MessageHeader messageHeader;

    @JsonProperty("Event")
    private Event event;
}
