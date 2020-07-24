package com.covid.clove.azure.models.healthmodels;

import lombok.Data;

import java.util.List;

@Data
public class ReadDataListResponse {
    private List<ReadDataResponse> readDataResponseList;
}
