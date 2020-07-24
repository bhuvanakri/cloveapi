package com.covid.clove.azure.models.healthmodels;

import lombok.Data;

@Data
public class PersonalDetailsResponse {
    private String fullName;
    private String age;
    private String gender;
    private String emergencyContact;
    private String bloodGroup;
    private String height;
    private String weight;
    private String address;
}
