package com.covid.clove.azure.models.bookingmodels.domain;

import lombok.Data;

import java.util.List;

@Data
public class HotelDomain {
    private String code;
    private String hotelName;
    private String latitude;
    private String longitude;
    private String categoryName;
    private List<RoomDetails> rooms;
    private double minRate;
    private double maxRate;
    private String currency;
}
