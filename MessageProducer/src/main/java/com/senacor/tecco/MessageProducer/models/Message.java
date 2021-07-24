package com.senacor.tecco.MessageProducer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static Message generate() {
        Event event = Event.generate(Event.EventType.Vorankündigung);
        return new Message(
                MessageHeader.generate(),
                event
        );
    }

    @Override
    public String toString(){
        ObjectMapper mapper = new ObjectMapper();
        String message = "";
        try {
            message = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return message;
    }

}
