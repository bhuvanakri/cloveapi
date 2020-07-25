package com.covid.clove.azure.models.healthmodels.domain;

import lombok.Data;

@Data
public class Address {
    private String[] line;
    private String city;
    private String postalCode;
}
