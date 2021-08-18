package com.senacor.tecco.MessageReadService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private MessageHeader messageHeader;

    private Event event;
}
