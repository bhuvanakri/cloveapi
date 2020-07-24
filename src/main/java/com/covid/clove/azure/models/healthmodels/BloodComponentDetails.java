package com.covid.clove.azure.models.healthmodels;

import lombok.Data;

@Data
public class BloodComponentDetails {
    private String rbc_count;
    private String wbc_count;
    private String haemoglobin;
    private String platelets;
}
