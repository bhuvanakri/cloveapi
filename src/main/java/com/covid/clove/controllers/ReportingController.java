package com.covid.clove.controllers;

import com.covid.clove.health.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/report")
public class ReportingController {
    private static final String PROJECT_ID = "getProjectid";
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
    public ResponseEntity<Object> getData() {
        String datasetId = "getid";
        String datasetName =
                String.format("projects/%s/locations/%s/datasets/%s", PROJECT_ID, REGION_ID, datasetId);
        try {
            return ResponseEntity.ok().body(GetDataSetImpl.datasetGet(datasetName));

        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return ResponseEntity.ok().body("get success");
    }

    @RequestMapping(value = "/deidentifydata")
    public ResponseEntity<Object> deidentifyData() {
        String datasetId = "getid";
        String datasetName =
                String.format("projects/%s/locations/%s/datasets/%s", PROJECT_ID, REGION_ID, datasetId);
        try {
            return ResponseEntity.ok().body(DeIdentifyImpl.datasetDeIdentify(datasetName, datasetName + "-died"));
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return ResponseEntity.ok().body("get success");
    }

    @RequestMapping(value = "/fhircreatestore")
    public ResponseEntity<Object> fhircreateStore() {
        String datasetId = "getid";
        String datasetName =
                String.format("projects/%s/locations/%s/datasets/%s", PROJECT_ID, REGION_ID, datasetId);
        String fhirStoreId = "my-fhir-store-1";
        String fhirStoreName = String.format("%s/fhirStores/%s", datasetName, fhirStoreId);
        try {
            return ResponseEntity.ok().body(FHIRStoreCreateImpl.fhirStoreCreate(datasetName, fhirStoreId));
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return ResponseEntity.ok().body("get success");
    }

    @RequestMapping(value = "/fhircreateresource")
    public ResponseEntity fhircreateResource() {

        String datasetId = "getid";
        String datasetName =
                String.format("projects/%s/locations/%s/datasets/%s", PROJECT_ID, REGION_ID, datasetId);
        String fhirStoreId = "my-fhir-store-1";
        String fhirStoreName = String.format("%s/fhirStores/%s", datasetName, fhirStoreId);
        try {
            return ResponseEntity.ok().body(EntityUtils.toString(FHIRResourceCreateImpl.fhirResourceCreate(fhirStoreName, "Patient")));
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/fhirgetpatientrecord")
    public ResponseEntity fhirGetPatientRecord() {
        String FHIR_NAME =
                "projects/%s/locations/%s/datasets/%s/fhirStores/%s/fhir/%s";
        String datasetId = "getid";
        String fhirStoreId = "my-fhir-store-1";
        String fhirResourceId = "Patient/getresouceid";
        String fhirResourceName = String.format(FHIR_NAME, PROJECT_ID, REGION_ID, datasetId, fhirStoreId, fhirResourceId);

        try {
            return ResponseEntity.ok().body(EntityUtils.toString(FHIRGetPatientRecordImpl.fhirResourceGetPatientEverything(fhirResourceName)));
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return null;
    }
    @RequestMapping(value = "/fhirgetresourcerecord")
    public ResponseEntity fhirGetResourceRecord() {
        String FHIR_NAME =
                "projects/%s/locations/%s/datasets/%s/fhirStores/%s/fhir/%s";
        String datasetId = "getid";
        String fhirStoreId = "my-fhir-store-1";
        String fhirResourceId = "Patient/getresouceid";
        String fhirResourceName = String.format(FHIR_NAME, PROJECT_ID, REGION_ID, datasetId, fhirStoreId, fhirResourceId);

        try {
            return ResponseEntity.ok().body(EntityUtils.toString(FHIRGetResourceRecord.fhirResourceGet(fhirResourceName)));
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return null;
    }



}
