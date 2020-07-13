package com.covid.clove.controllers;

import com.covid.clove.health.CreateDataSetImpl;
import com.covid.clove.health.GetDataSetImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/report")
public class ReportingController {
    private static final String PROJECT_ID = System.getenv("PROJECT_ID");
    private static final String REGION_ID = "us-central1";
    @RequestMapping(value = "/createdata")
    public ResponseEntity<Object> createaData() {
        String datasetId = "dataset-" + UUID.randomUUID().toString().replaceAll("-", "_");
        String datasetName =
                String.format("projects/%s/locations/%s/datasets/%s", PROJECT_ID, REGION_ID, datasetId);
        try {
            return ResponseEntity.ok().body(CreateDataSetImpl.datasetCreate(PROJECT_ID, REGION_ID, datasetId));
        } catch (Exception exp) {
            exp.printStackTrace();
        }
       return ResponseEntity.ok().body("SUCCESS");
    }

    @RequestMapping(value = "/getdata")
    public ResponseEntity<Object> getdata() {
        String datasetId = "dataset-" + UUID.randomUUID().toString().replaceAll("-", "_");
        String datasetName =
                String.format("projects/%s/locations/%s/datasets/%s", PROJECT_ID, REGION_ID, datasetId);
        try {
            return ResponseEntity.ok().body(GetDataSetImpl.datasetGet(datasetName));

        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return ResponseEntity.ok().body("get success");
    }



}
