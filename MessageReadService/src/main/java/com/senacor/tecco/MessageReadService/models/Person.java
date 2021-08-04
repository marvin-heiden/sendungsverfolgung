package com.senacor.tecco.MessageReadService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Street")
    private String street;

    @JsonProperty("HouseNumber")
    private String houseNumber;

    @JsonProperty("City")
    private String city;

    @JsonProperty("PostCode")
    private String postCode;

    @JsonProperty("CityDistrict")
    private String cityDistrict;

    @JsonProperty("PostBox")
    private String postBox;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Fax")
    private String fax;
}
