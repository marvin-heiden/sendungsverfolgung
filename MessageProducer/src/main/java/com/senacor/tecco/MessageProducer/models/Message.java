package com.senacor.tecco.MessageProducer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @JsonProperty("MessageHeader")
    private MessageHeader messageHeader;

    @JsonProperty("Event")
    private Event event;

    public static Message generate() {
        Event event = Event.generate(Event.EventType.Vorankündigung);
        return new Message(
                MessageHeader.generate(),
                event
        );
    }

}
