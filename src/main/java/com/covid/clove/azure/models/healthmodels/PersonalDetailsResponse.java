package com.covid.clove.azure.models.healthmodels;

import lombok.Data;

@Data
public class PersonalDetailsResponse {
    private String fullName;
    private String age;
    private String dob;
    private String gender;
    private String emergencyContact;
    private String address;
    private String height; //8302-2
    private String weight; //29463-7
    private String bloodGroup; //883-9
}
