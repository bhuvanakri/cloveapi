package com.covid.clove.azure.controllers;

import com.covid.clove.azure.implementation.HealthReportImplementation;
import com.covid.clove.azure.mapper.HealthMapper;
import com.covid.clove.azure.mocks.MockHealthResponse;
import com.covid.clove.azure.models.healthmodels.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class HealthController {

    @RequestMapping(value = "/personalDetails")
    public PersonalDetailsResponse fetchPersonalDetails() {
        PersonalDetailsResponse response = HealthMapper.mapPersonalDetails(HealthReportImplementation.fetchPersonalInfo());
        response = HealthReportImplementation.fetchBodyMetric(response);
        return response;
        //return MockHealthResponse.mockPersonalDetails();
    }

    @RequestMapping(value = "/hospitalDetails")
    public List<HospitalsDetailsResponse> fetchHospitalDetails() {
        return HealthMapper.mapHospitalDetailsList(HealthReportImplementation.fetchHospitalInfo());
        //return MockHealthResponse.mockHospitalDetails();
    }

    @RequestMapping(value = "/healthHistory")
    public HealthHistoryResponse fetchHealthHistory() {
        return MockHealthResponse.mockHealthHistory();
    }

    @RequestMapping(value = "/bloodComponentDetails")
    public BloodComponentDetails fetchBloodComponentDetails() {
        BloodComponentDetails response = HealthReportImplementation.fetchBloodComponentDetails(new BloodComponentDetails());
        return response;
        //return MockHealthResponse.mockBloodComponentDetails();
    }

    @RequestMapping(value = "/readData")
    public ReadDataListResponse fetchReportList() {
        return HealthReportImplementation.fetchObservationListReport(new ReadDataListResponse());
       // return MockHealthResponse.mockReadDataListResponse();
    }

    @RequestMapping(value = "/readLatestData")
    public ReadDataResponse fetchCurrentReport() {
        return HealthReportImplementation.fetchObservationReport(new ReadDataResponse());
        //return MockHealthResponse.mockReadDataResponse();
    }

}
