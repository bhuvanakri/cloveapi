package com.covid.clove.azure.models.healthmodels.domain;

import lombok.Data;

@Data
public class Coding {

    private String system;
    private String code;
    private String display;
}
