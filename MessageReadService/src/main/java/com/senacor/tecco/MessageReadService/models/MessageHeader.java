package com.senacor.tecco.MessageReadService.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

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
}
