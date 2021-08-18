package com.senacor.tecco.MessageProducer.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.andreinc.mockneat.MockNeat;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageHeader {

    @JsonProperty("MsgUUID")
    private String msgUuid;

    @JsonProperty("MsgSender")
    private String msgSender;

    @JsonProperty("MsgReceiver")
    private String msgReceiver;

    @JsonProperty("MsgTimestamp")
    private Instant msgTimestamp;

    public static MessageHeader generate(){
        return new MessageHeader(
                UUID.randomUUID().toString(),
                "MessageProducer",
                "TrackingService",
                Instant.now()
        );
    }
}
