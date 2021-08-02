package com.senacor.tecco.MessageFilterService.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {


    @JsonProperty("EventUUID")
    private String uuid;

    @JsonProperty("EventTimestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date creationTimestamp;

    @JsonProperty("EventType")
    private String type;

    @JsonProperty("ScanFacility")
    private String facility;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Sender")
    private Person sender;

    @JsonProperty("Receiver")
    private Person receiver;

    @JsonProperty("Identifiers")
    private ArrayList<Identifier> identifiers;

}




