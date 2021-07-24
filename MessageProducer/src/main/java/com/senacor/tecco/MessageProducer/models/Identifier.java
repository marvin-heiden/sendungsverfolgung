package com.senacor.tecco.MessageProducer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mifmif.common.regex.Generex;
import lombok.*;
import net.andreinc.mockneat.MockNeat;

import java.util.Locale;

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

    public static Identifier generate(){
        MockNeat mock = MockNeat.threadLocal();
        return new Identifier(
                "Versandmarke",
                mock.regex("\\b([0-9]{12}|100\\d{31}|\\d{15}|\\d{18}|96\\d{20}|96\\d{32})\\b").get(),
                Float.parseFloat(String.format(Locale.ROOT,"%.2f",10*Math.random())),
                "EUR"
        );
    }


}
