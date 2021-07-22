package com.senacor.tecco.MessageFilterService.models;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
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
