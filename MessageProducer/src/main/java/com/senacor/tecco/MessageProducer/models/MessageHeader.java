package com.senacor.tecco.MessageProducer.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.andreinc.mockneat.MockNeat;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageHeader {
    @Getter
    @Setter
    @JsonProperty("MsgUUID")
    private String msgUuid;
    @Getter
    @Setter
    @JsonProperty("MsgSender")
    private String msgSender;
    @Getter
    @Setter
    @JsonProperty("MsgReceiver")
    private String msgReceiver;
    @Getter
    @Setter
    @JsonProperty("MsgTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date msgTimestamp;

    public static MessageHeader generate(){
        return new MessageHeader(
                UUID.randomUUID().toString(),
                "MessageProducer",
                "TrackingService",
                new Date()
        );
    }
}
