package com.senacor.tecco.MessageFilterService.models;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class Identifier {
    private String type;
    private String value;
    private float amount;
    private String currency;
}
