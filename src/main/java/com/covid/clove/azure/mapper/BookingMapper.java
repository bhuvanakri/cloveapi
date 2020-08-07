package com.covid.clove.azure.mapper;

import com.covid.clove.azure.models.bookingmodels.Hotel;
import com.covid.clove.azure.models.bookingmodels.HotelDetails;
import com.covid.clove.azure.models.bookingmodels.domain.HotelDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.covid.clove.azure.models.bookingmodels.domain.RateDetails;
import com.covid.clove.azure.models.bookingmodels.domain.RoomDetails;

import io.restassured.response.Response;

public class BookingMapper {

    public static List<Hotel> mapHotels(List<HotelDomain> hotelsDomainList) {
        List<Hotel> hList = new ArrayList<Hotel>();
        for(HotelDomain h:hotelsDomainList) {
            Hotel hotel = new Hotel();
            hotel.setId(h.getCode());
            hotel.setLatitude(h.getLatitude());
            hotel.setLongitude(h.getLongitude());
            hotel.setName(h.getHotelName());
            hotel.setHotelRating(h.getCategoryName());
            hotel.setThumbNailUrl("http://media.expedia.com");
            hotel.setPrice(getPrice(h));
            if(h.getRooms().size() > 0){
                List<RateDetails> ratesList = h.getRooms().get(0).getRates();
                if(ratesList.size() > 0) {
                    hotel.setShortDescription(ratesList.get(0).getBoardName());
                    hotel.setRooms(h.getRooms()); // only if rate is there we will add that hotel
                    hList.add(hotel);
                }
            }
        }
        return hList;
    }

    private static double getPrice(HotelDomain h) {
        if (h.getCurrency().equalsIgnoreCase("EUR")) {
            double minrate = h.getMinRate();
            double maxrate = h.getMaxRate();
            h.setMaxRate(maxrate * 88);
            h.setMinRate(minrate * 88);
            h.setCurrency("INR");
        }
        return h.getMinRate();
    }

    public static HotelDetails mapHotelDetails(String hotelName, List<Hotel> hotels) {
        for (Hotel htl : hotels) {
            if (htl.getName().equalsIgnoreCase(hotelName)) {
                HotelDetails hotel = new HotelDetails();
                hotel.setTotalRooms(htl.getRooms().size() + ((htl.getRooms().size()) / 2));
                hotel.setOccuipedRooms(htl.getRooms().size() - ((htl.getRooms().size()) / 2));
                hotel.setAvailableRooms(htl.getRooms().size());
                hotel.setAddress(htl.getLocationInfo().getDisplay_name());
                hotel.setAmenities(htl.getShortDescription());
                hotel.setDisplayName(htl.getName());
                return hotel;
            }
        }
        return null;
    }

    public static List<HotelDomain> mapHotelDomains(Response response, List<Map<Object, Object>> hotels) {
        List<HotelDomain> hotelDomainList = new ArrayList<HotelDomain>();
        for (int i = 0; i < 10 - 1; i++) {
            HotelDomain domain = new HotelDomain();
            domain.setCode(response.jsonPath().getString("hotels.hotels[" + i + "].code"));
            domain.setCategoryName(response.jsonPath().getString("hotels.hotels[" + i + "].categoryName"));
            domain.setHotelName(response.jsonPath().getString("hotels.hotels[" + i + "].name"));
            domain.setCurrency(response.jsonPath().getString("hotels.hotels[" + i + "].currency"));
            domain.setLatitude(response.jsonPath().getString("hotels.hotels[" + i + "].latitude"));
            domain.setLongitude(response.jsonPath().getString("hotels.hotels[" + i + "].longitude"));
            domain.setMinRate(new Double(response.jsonPath().getString("hotels.hotels[" + i + "].minRate")));
            domain.setMaxRate(new Double(response.jsonPath().getString("hotels.hotels[" + i + "].maxRate")));
            List<Map<Object, Object>> rooms = response.jsonPath().getList("hotels.hotels[" + i + "].rooms");
            List<RoomDetails> roomDetailsList = new ArrayList<RoomDetails>();
            for (int j = 0; j < rooms.size() - 1; j++) {
                RoomDetails roomDetails = new RoomDetails();
                roomDetails.setCode(response.jsonPath().getString("hotels.hotels[" + i + "].rooms[" + j + "].code"));
                roomDetails.setName(response.jsonPath().getString("hotels.hotels[" + i + "].rooms[" + j + "].name"));
                List<Map<Object, Object>> rates = response.jsonPath().getList("hotels.hotels[" + i + "].rooms[" + j + "].rates");
                List<RateDetails> ratesList = new ArrayList<RateDetails>();
                for (int k = 0; k < rates.size() - 1; k++) {
                    RateDetails rateDetails = new RateDetails();
                    rateDetails.setBoardName(response.jsonPath().getString("hotels.hotels[" + i + "].rooms[" + j + "].rates[" + k + "].boardName"));
                    rateDetails.setRateKey(response.jsonPath().getString("hotels.hotels[" + i + "].rooms[" + j + "].rates[" + k + "].rateKey"));
                    ratesList.add(rateDetails);
                }
                roomDetails.setRates(ratesList);
                roomDetailsList.add(roomDetails);
            }
            domain.setRooms(roomDetailsList);
            hotelDomainList.add(domain);
        }
        return hotelDomainList;
    }

}
