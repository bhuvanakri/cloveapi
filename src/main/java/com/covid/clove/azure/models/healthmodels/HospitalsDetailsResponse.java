package com.covid.clove.azure.models.healthmodels;

import lombok.Data;

@Data
public class HospitalsDetailsResponse {
    private String hospitalName;
    private String hospitalContactNumber;
    private String hospitalAddress;
    private String hospitalArea;
}
