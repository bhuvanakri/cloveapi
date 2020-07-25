package com.covid.clove.azure.models.healthmodels.domain;

import lombok.Data;

@Data
public class OrgObservation {
    private String fullUrl;
    private OrgResourceDetails resource;
}
