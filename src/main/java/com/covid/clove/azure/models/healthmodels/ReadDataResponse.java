package com.covid.clove.azure.models.healthmodels;

import lombok.Data;

@Data
public class ReadDataResponse {
    private String Date;
    private String AGGREGATE_BLOOD_PRESSURE; //55284-4 - systoli:8480-6, diastoli: 8462-4
    private String AGGREGATE_BLOOD_GLUCOSE; //2339-0
    private String AGGREGATE_OXYGEN_SATURATION; //2713-6 https://loinc.org/2713-6/
    private String AGGREGATE_BODY_TEMPERATURE; //8310-5
    private String AGGREGATE_HEART_RATE; //8867-4
}
