package com.covid.clove.azure.models.healthmodels.domain;

import lombok.Data;

import java.util.List;

@Data
public class ResourceDetails {
    private Code code;
    private ValueQuantity valueQuantity;
    private Code valueCodeableConcept;

    //Patient
    private String id;
    private List<Name> name;
    private String birthDate;
    private List<Address> address;
    private String gender;
    private List<Telecom> telecom;
    private String effectiveDateTime;
}
