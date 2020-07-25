package com.covid.clove.azure.models.healthmodels.domain;

import lombok.Data;

@Data
public class Observation {
    private String fullUrl;
    private ResourceDetails resource;
}
