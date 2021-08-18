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

    private String name;

    private String street;

    private String houseNumber;

    private String city;

    private String postCode;

    private String cityDistrict;

    private String postBox;

    private String country;

    private String email;

    private String phone;

    private String fax;
}
