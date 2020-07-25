package com.covid.clove.azure.models.healthmodels.domain;

import lombok.Data;

import java.util.List;

@Data
public class OrgResourceDetails {
    private String id;
    private String name;
    private List<Address> address;
    private List<Telecom> telecom;
}
