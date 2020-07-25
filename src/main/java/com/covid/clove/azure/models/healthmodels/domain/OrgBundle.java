package com.covid.clove.azure.models.healthmodels.domain;

import lombok.Data;

import java.util.List;

@Data
public class OrgBundle {
    private List<OrgObservation> entry;
}
