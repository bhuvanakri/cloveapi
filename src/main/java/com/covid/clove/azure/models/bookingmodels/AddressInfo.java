package com.covid.clove.azure.models.bookingmodels;

import lombok.Data;

@Data
public class AddressInfo {
    private String road;
    private String neighbourhood;
    private String suburb;
    private String city_district;
    private String city;
    private String county;
    private String state_district;
    private String state;
    private String postcode;
    private String country;
    private String country_code;
    private String formattedAddress;


}
