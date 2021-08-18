package com.senacor.tecco.MessageFilterService.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageHeader {

    @JsonProperty("MsgUUID")
    private String msgUuid;

    @JsonProperty("MsgSender")
    private String msgSender;

    @JsonProperty("MsgReceiver")
    private String msgReceiver;

    @JsonProperty("MsgTimestamp")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Instant msgTimestamp;
}
