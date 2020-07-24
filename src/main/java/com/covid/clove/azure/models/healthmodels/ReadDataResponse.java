package com.covid.clove.azure.models.healthmodels;

import lombok.Data;

@Data
public class ReadDataResponse {
    private String Date;
    private String AGGREGATE_BLOOD_PRESSURE;
    private String AGGREGATE_BLOOD_GLUCOSE;
    private String AGGREGATE_OXYGEN_SATURATION;
    private String AGGREGATE_BODY_TEMPERATURE;
    private String AGGREGATE_HEART_RATE;
}
