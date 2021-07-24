package com.senacor.tecco.MessageProducer.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mifmif.common.regex.Generex;
import lombok.*;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnitString;
import net.andreinc.mockneat.types.enums.StringType;

import java.util.Random;

import static net.andreinc.mockneat.types.enums.StringFormatType.CAPITALIZED;
import static net.andreinc.mockneat.types.enums.StringType.NUMBERS;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    @Getter
    @Setter
    @JsonProperty("Name")
    private String name;
    @Getter
    @Setter
    @JsonProperty("Street")
    private String street;
    @Getter
    @Setter
    @JsonProperty("HouseNumber")
    private String houseNumber;
    @Getter
    @Setter
    @JsonProperty("City")
    private String city;
    @Getter
    @Setter
    @JsonProperty("PostCode")
    private String postCode;
    @Getter
    @Setter
    @JsonProperty("CityDistrict")
    private String cityDistrict;
    @Getter
    @Setter
    @JsonProperty("PostBox")
    private String postBox;
    @Getter
    @Setter
    @JsonProperty("Country")
    private String country;
    @Getter
    @Setter
    @JsonProperty("Email")
    private String email;
    @Getter
    @Setter
    @JsonProperty("Phone")
    private String phone;
    @Getter
    @Setter
    @JsonProperty("Fax")
    private String fax;

    public static Person generate(){
        MockNeat mock = MockNeat.threadLocal();

        MockUnitString streetGenerator = mock.fmt("#{name}-#{suffix}")
                .param("name", mock.words().nouns().format(CAPITALIZED))
                .param("suffix", mock.probabilites(String.class)
                        .add(0.25, "Allee")
                        .add(0.25, "Weg")
                        .add(0.50, "Straße")
                        .mapToString());

        return new Person(
                mock.names().full().get(), // Name
                streetGenerator.get() , // Street
                mock.strings().size(mock.ints().range(1,5)).type(NUMBERS).get(), // HouseNumber
                mock.cities().capitalsEurope().get(), // City
                mock.strings().size(mock.ints().range(1,10)).type(NUMBERS).get(), // PostCode
                mock.cities().us().get(), // CityDistrict
                mock.strings().size(mock.ints().range(1,10)).type(NUMBERS).get(), // PostBox
                "DE", // Country
                mock.emails().get(),
                mock.regex("\\+[0-9]{2}-[0-9]{4}-[0-9]{7}").get(),
                mock.regex("\\+[0-9]{2}-[0-9]{4}-[0-9]{7}").get()
                );
    }
}
