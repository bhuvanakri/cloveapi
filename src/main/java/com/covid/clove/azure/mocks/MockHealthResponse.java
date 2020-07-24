package com.covid.clove.azure.mocks;

import com.covid.clove.azure.models.healthmodels.*;
import org.springframework.format.datetime.DateFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockHealthResponse {
   static SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
    public static ReadDataResponse mockReadDataResponse()
    {
        ReadDataResponse response = new ReadDataResponse();
        response.setAGGREGATE_BLOOD_GLUCOSE("115"); //mg/dL
        response.setAGGREGATE_BLOOD_PRESSURE("139/89"); //mm/Hg (systolic/diastolic)
        response.setAGGREGATE_BODY_TEMPERATURE("99.4"); //fahrenheit
        response.setAGGREGATE_HEART_RATE("72"); //bpm
        response.setAGGREGATE_OXYGEN_SATURATION("95"); //sPO2 %
        response.setDate(formatter.format(new Date()));
        //to mock
        return response;

    }

    public static ReadDataListResponse mockReadDataListResponse()
    {
        ReadDataListResponse response = new ReadDataListResponse();
        List<ReadDataResponse> readDataResponses = new ArrayList<ReadDataResponse>();
        ReadDataResponse dataresponse = new ReadDataResponse();
        readDataResponses.add(dataresponse);
        response.setReadDataResponseList(readDataResponses);
        return response;
    }

    public static HospitalsDetailsResponse mockHospitalDetails()
    {
        HospitalsDetailsResponse response = new HospitalsDetailsResponse();
        response.setHospitalAddress("1, pallikaranai road, palikaranai, chennai-600007");
        response.setHospitalArea("pallikaranai");
        response.setHospitalContactNumber("7655555555");
        response.setHospitalName("Bethsada hospital");
        //to mock
        return response;

    }

    public static PersonalDetailsResponse mockPersonalDetails()
    {
        PersonalDetailsResponse response = new PersonalDetailsResponse();
        response.setFullName("Aadhya Srinivas");
        response.setAddress("13,CLOVE apartments, Shollinganallur");
        response.setAge("24");
        response.setGender("Male");
        response.setHeight("157");
        response.setWeight("65");
        response.setEmergencyContact("73555 55555");
        response.setBloodGroup("B+");
        //to mock
        return response;

    }

    public static BloodComponentDetails mockBloodComponentDetails()
    {
        BloodComponentDetails response = new BloodComponentDetails();
        response.setHaemoglobin("13"); //g/dL
        response.setRbc_count("439");  //10000/ml
        response.setWbc_count("10,850");  //ml
        response.setPlatelets("33.8"); //10000/ml
        //to mock
        return response;
    }

    public static HealthHistoryResponse mockHealthHistory()
    {
        HealthHistoryResponse response = new HealthHistoryResponse();
       response.setCardio("Good Heart condition");
       response.setPhysio("Arthitis history observed");
       response.setDiabetes("In insulin administration with 3mm per day");
        //to mock
        return response;
    }

}
