package com.covid.clove.azure.implementation;

import com.covid.clove.azure.models.healthmodels.BloodComponentDetails;
import com.covid.clove.azure.models.healthmodels.PersonalDetailsResponse;
import com.covid.clove.azure.models.healthmodels.ReadDataListResponse;
import com.covid.clove.azure.models.healthmodels.ReadDataResponse;
import com.covid.clove.azure.models.healthmodels.domain.ItemModel;
import com.covid.clove.azure.models.healthmodels.domain.Name;
import com.covid.clove.azure.models.healthmodels.domain.Observation;
import com.covid.clove.azure.models.healthmodels.domain.OrgObservation;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HealthReportImplementation {

    public static ItemModel fetchPersonalInfo() {
        Observation o =BaseImpl.fetchPatient("Tamil");
        ItemModel patientPersonalInfo = new ItemModel();
        patientPersonalInfo.setAddress(o.getResource().getAddress());
        patientPersonalInfo.setName(o.getResource().getName());
        patientPersonalInfo.setTelecom(o.getResource().getTelecom());
        patientPersonalInfo.setBirthDate(o.getResource().getBirthDate());
        patientPersonalInfo.setGender(o.getResource().getGender());
        patientPersonalInfo.setId(o.getResource().getId());
        return patientPersonalInfo;
    }
    public static PersonalDetailsResponse fetchBodyMetric(PersonalDetailsResponse response) {
        Observation htObservation = BaseImpl.fetchObservation("8302-2");
        if(htObservation != null) {
            response.setHeight(htObservation.getResource().getValueQuantity().getValue().toString() +" "+ htObservation.getResource().getValueQuantity().getUnit().toString());
        }
        Observation wtObservation = BaseImpl.fetchObservation("29463-7");
        if(wtObservation != null) {
            response.setWeight(wtObservation.getResource().getValueQuantity().getValue().toString() +" "+ wtObservation.getResource().getValueQuantity().getUnit().toString());
        }
        Observation bgObservation = BaseImpl.fetchObservation("883-9");
        if(bgObservation != null){
            response.setBloodGroup(bgObservation.getResource().getValueCodeableConcept().getText());
        }

        return response;
    }

    public static List<ItemModel> fetchHospitalInfo() {
        List<OrgObservation> hospList = BaseImpl.fetchHospitals("india");
        List<ItemModel> itemsList = new ArrayList<ItemModel>();
        for(OrgObservation o: hospList)
        {
            ItemModel item = new ItemModel();
            item.setAddress(o.getResource().getAddress());
            List<Name> nList = new ArrayList<Name>();
            Name n = new Name();
            n.setText(o.getResource().getName());
            nList.add(n);
            item.setName(nList);
            item.setTelecom(o.getResource().getTelecom());
            item.setId(o.getResource().getId());
            itemsList.add(item);
        }
        return itemsList;
    }

    //BloodComponentDetails
    public static BloodComponentDetails fetchBloodComponentDetails(BloodComponentDetails response) {
        Observation plateletsObservation = BaseImpl.fetchObservation("777-3");
        if(plateletsObservation != null) {
            response.setPlatelets(plateletsObservation.getResource().getValueQuantity().getValue().toString()+" "+ plateletsObservation.getResource().getValueQuantity().getUnit().toString());
        }
        Observation wbcObservation = BaseImpl.fetchObservation("6690-2");
        if(wbcObservation != null) {
            response.setWbc_count(wbcObservation.getResource().getValueQuantity().getValue().toString()+" "+wbcObservation.getResource().getValueQuantity().getUnit().toString());
        }
        Observation rbcObservation = BaseImpl.fetchObservation("789-8");
        if(rbcObservation != null){
            response.setRbc_count(rbcObservation.getResource().getValueQuantity().getValue().toString()+" "+rbcObservation.getResource().getValueQuantity().getUnit().toString());
        }
        Observation hgObservation = BaseImpl.fetchObservation("718-7");
        if(hgObservation != null){
            response.setHaemoglobin(hgObservation.getResource().getValueQuantity().getValue().toString()+" "+ hgObservation.getResource().getValueQuantity().getUnit().toString());
        }

        return response;
    }

    //ObservationCurrentData
    public static ReadDataResponse fetchObservationReport(ReadDataResponse response) {

        Observation heartRateObs = BaseImpl.fetchObservation("8867-4");
        if(heartRateObs != null) {
            response.setAGGREGATE_HEART_RATE(heartRateObs.getResource().getValueQuantity().getValue().toString()+" "+heartRateObs.getResource().getValueQuantity().getUnit().toString());
            response.setDate(heartRateObs.getResource().getEffectiveDateTime());
        }
        Observation bodyTemp = BaseImpl.fetchObservation("8310-5");
        if(bodyTemp != null){
            response.setAGGREGATE_BODY_TEMPERATURE(bodyTemp.getResource().getValueQuantity().getValue().toString()+" "+bodyTemp.getResource().getValueQuantity().getUnit().toString());
        }
        Observation glucoseObs = BaseImpl.fetchObservation("2339-0");
        if(glucoseObs != null){
            response.setAGGREGATE_BLOOD_GLUCOSE(glucoseObs.getResource().getValueQuantity().getValue().toString()+" "+ glucoseObs.getResource().getValueQuantity().getUnit().toString());
        }

        Observation sysbloodPressure = BaseImpl.fetchObservation("8480-6");
        String sysBP = null;
        if(sysbloodPressure != null){
            sysBP= (sysbloodPressure.getResource().getValueQuantity().getValue().toString()+" "+ sysbloodPressure.getResource().getValueQuantity().getUnit().toString());
        }

        Observation dibloodPressure = BaseImpl.fetchObservation("8462-4");
        String diBP = null;
        if(dibloodPressure != null){
            diBP= (dibloodPressure.getResource().getValueQuantity().getValue().toString()+" "+ dibloodPressure.getResource().getValueQuantity().getUnit().toString());
        }
        response.setAGGREGATE_BLOOD_PRESSURE(sysBP+"/"+diBP);
        /*Observation oxySaturation = BaseImpl.fetchObservation("2713-6");
        if(oxySaturation != null) {
            response.setAGGREGATE_OXYGEN_SATURATION(oxySaturation.getResource().getValueQuantity().getValue().toString()+" "+ oxySaturation.getResource().getValueQuantity().getUnit().toString());
        }*/
        return response;
    }

    //ObservationListData
    public static ReadDataListResponse fetchObservationListReport(ReadDataListResponse response) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        List<ReadDataResponse> dailyresponse = new ArrayList<ReadDataResponse>();
        for(int i=0;i<10;i++) {
            ReadDataResponse rs = new ReadDataResponse();
            dailyresponse.add(fetchObservationReport(rs));
            i++;
        }
        response.setReadDataResponseList(dailyresponse);
        return response;
    }

}
