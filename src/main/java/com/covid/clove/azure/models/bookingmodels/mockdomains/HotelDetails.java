package com.covid.clove.azure.models.bookingmodels.mockdomains;

public class HotelDetails {
	
	private String displayName;
	private int totalRooms;
	private int occuipedRooms;
	private int availableRooms;
	private String address;
	private String amenities;
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public int getTotalRooms() {
		return totalRooms;
	}
	public void setTotalRooms(int totalRooms) {
		this.totalRooms = totalRooms;
	}
	public int getOccuipedRooms() {
		return occuipedRooms;
	}
	public void setOccuipedRooms(int occuipedRooms) {
		this.occuipedRooms = occuipedRooms;
	}
	public int getAvailableRooms() {
		return availableRooms;
	}
	public void setAvailableRooms(int availableRooms) {
		this.availableRooms = availableRooms;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAmenities() {
		return amenities;
	}
	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

}
