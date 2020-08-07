package com.covid.clove.azure.models.bookingmodels;

import lombok.Data;

@Data
public class HotelDetails {
	private String displayName;
	private int totalRooms;
	private int occuipedRooms;
	private int availableRooms;
	private String address;
	private String amenities;

}
