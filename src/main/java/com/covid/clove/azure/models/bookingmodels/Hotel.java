package com.covid.clove.azure.models.bookingmodels;

import com.covid.clove.azure.models.bookingmodels.domain.RoomDetails;
import lombok.Data;

import java.util.List;
@Data
public class Hotel {
	private String name;
	private String latitude;
	private String longitude;
	private double price;
	private String shortDescription;
	private String thumbNailUrl;
	private String hotelRating;
	private String id;
	private LocationInfo locationInfo;
	private List<RoomDetails> rooms;
}
