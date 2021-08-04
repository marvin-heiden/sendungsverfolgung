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
public class Identifier {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Value")
    private String value;

    @JsonProperty("Amount")
    private float amount;

    @JsonProperty("Currency")
    private String currency;
}
