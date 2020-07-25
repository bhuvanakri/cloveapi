package com.covid.clove.azure.mapper;

import com.covid.clove.azure.models.healthmodels.HospitalsDetailsResponse;
import com.covid.clove.azure.models.healthmodels.domain.ItemModel;
import com.covid.clove.azure.models.healthmodels.PersonalDetailsResponse;
import com.covid.clove.azure.models.healthmodels.domain.Telecom;

import java.util.ArrayList;
import java.util.List;

public class HealthMapper {

    public static PersonalDetailsResponse mapPersonalDetails(ItemModel patient)
    {
       PersonalDetailsResponse response = new PersonalDetailsResponse();
        response.setAddress((patient.getAddress() == null) ? null:patient.getAddress().get(0).toString());
        response.setFullName((patient.getName() == null) ? null:patient.getName().get(0).getText());
        response.setGender(patient.getGender());
       response.setEmergencyContact((patient.getTelecom() == null) ? null:patient.getTelecom().get(0).getValue());
        response.setDob(patient.getBirthDate());
       return response;
    }
    public static HospitalsDetailsResponse mapHospitalDetails(ItemModel organization)
    {
        HospitalsDetailsResponse response = new HospitalsDetailsResponse();
        response.setHospitalAddress((organization.getAddress() == null) ? null:organization.getAddress().get(0).toString());
        response.setHospitalArea((organization.getAddress() == null) ? null:organization.getAddress().get(0).getCity());
        response.setHospitalName(organization.getName().get(0).getText());
        for(Telecom t: organization.getTelecom()) {
            if (t.getSystem().equalsIgnoreCase("phone")) {
                response.setHospitalContactNumber(t.getValue());
                break;
            }
        }
        return response;
    }
    public static List<HospitalsDetailsResponse> mapHospitalDetailsList(List<ItemModel> organizationList)
    {
        List<HospitalsDetailsResponse> response = new ArrayList<HospitalsDetailsResponse>();
        for(ItemModel item: organizationList)
        {
            response.add(mapHospitalDetails(item));
        }
        return response;
    }
}
