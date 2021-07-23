package com.senacor.tecco.MessageReadService.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    @Getter
    @Setter
    @JsonProperty("EventUUID")
    private String uuid;
    @Getter
    @Setter
    @JsonProperty("EventTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date creationTimestamp;
    @Getter
    @Setter
    @JsonProperty("EventType")
    private String type;
    @Getter
    @Setter
    @JsonProperty("ScanFacility")
    private String facility;
    @Getter
    @Setter
    @JsonProperty("Message")
    private String message;
    @Getter
    @Setter
    @JsonProperty("Sender")
    private Person sender;
    @Getter
    @Setter
    @JsonProperty("Receiver")
    private Person receiver;
    @Getter
    @Setter
    @JsonProperty("Identifiers")
    private ArrayList<Identifier> identifiers;

}




