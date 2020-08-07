package com.covid.clove.azure.models.bookingmodels.domain;


import lombok.Data;

import java.util.List;

@Data
public class RoomDetails {
    private String code;
    private String name;
    private List<RateDetails> rates;
}
