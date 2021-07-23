package com.senacor.tecco.MessageReadService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
}
