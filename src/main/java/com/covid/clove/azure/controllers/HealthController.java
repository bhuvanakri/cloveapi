package com.covid.clove.azure.controllers;

import com.covid.clove.azure.mocks.MockHealthResponse;
import com.covid.clove.azure.models.healthmodels.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class HealthController {

    @RequestMapping(value = "/personalDetails")
    public PersonalDetailsResponse fetchPersonalDetails() {
        return MockHealthResponse.mockPersonalDetails();
    }

    @RequestMapping(value = "/bloodComponentDetails")
    public BloodComponentDetails fetchBloodComponentDetails() {
        return MockHealthResponse.mockBloodComponentDetails();
    }

    @RequestMapping(value = "/hospitalDetails")
    public HospitalsDetailsResponse fetchHospitalDetails() {
        return MockHealthResponse.mockHospitalDetails();
    }

    @RequestMapping(value = "/healthHistory")
    public HealthHistoryResponse fetchHealthHistory() {
        return MockHealthResponse.mockHealthHistory();
    }

    @RequestMapping(value = "/readData")
    public ReadDataListResponse fetchReportList() {
        return MockHealthResponse.mockReadDataListResponse();
    }

    @RequestMapping(value = "/readLatestData")
    public ReadDataResponse fetchCurrentReport() {
        return MockHealthResponse.mockReadDataResponse();
    }

}
