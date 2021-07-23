package com.senacor.tecco.MessageReadService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Identifier {
    @Getter
    @Setter
    @JsonProperty("Type")
    private String type;
    @Getter
    @Setter
    @JsonProperty("Value")
    private String value;
    @Getter
    @Setter
    @JsonProperty("Amount")
    private float amount;
    @Getter
    @Setter
    @JsonProperty("Currency")
    private String currency;
}
