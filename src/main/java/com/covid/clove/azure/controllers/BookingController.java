package com.covid.clove.azure.controllers;

import com.covid.clove.azure.implementation.BookingImplementation;
import com.covid.clove.azure.mapper.BookingMapper;
import com.covid.clove.azure.models.bookingmodels.City;
import com.covid.clove.azure.models.bookingmodels.Hotel;
import com.covid.clove.azure.models.bookingmodels.HotelDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PropertySource("classpath:mock.properties")
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private Environment env;
    static Map cityMap = new HashMap();
    static List<Hotel> hotels;

    static {
        cityMap.put("BOM", "Mumbai");
        cityMap.put("COK", "Cochin");
        cityMap.put("BLR", "Bangalore");
        cityMap.put("DEL", "Delhi");
        cityMap.put("HYD", "Hyderabad");
    }

    @GetMapping("/cities")
    public ResponseEntity<City> getCities() {
        List<String> cityList = new ArrayList<String>();
        cityList.addAll(cityMap.values());
        City city = new City();
        city.setCityList(cityList);
        return new ResponseEntity<City>(city, HttpStatus.OK);
    }

    @GetMapping("/{city}/location")
    public ResponseEntity<List<String>> getLocations(@PathVariable("city") final String city) {
        this.setHotels(null); //everytime when city changes - clear hotels list
        List<Hotel> hotels = (BookingMapper.mapHotels(BookingImplementation.fetchHotels(getKey(city))));
        this.setHotels(hotels);
        List<String> locationList = new ArrayList<String>();
        for (Hotel h : hotels) {
            BookingImplementation.fetchLocation(h);
            locationList.add(h.getLocationInfo().getDisplay_name());
        }
        return new ResponseEntity<List<String>>(locationList, HttpStatus.OK);
    }


    @GetMapping("/{city}/{location}/hotel")
    public ResponseEntity<List<Hotel>> getHotels(@PathVariable("city") final String city) {
        return new ResponseEntity<List<Hotel>>(this.getHotels(), HttpStatus.OK);
    }


    @GetMapping("/{city}/{location}/{hotel}/details")
    public ResponseEntity<HotelDetails> getDetails(@PathVariable("hotel") final String hotelId) {
        HotelDetails hotelDetails = BookingMapper.mapHotelDetails(hotelId, this.getHotels());
        return new ResponseEntity<HotelDetails>(hotelDetails, HttpStatus.OK);
    }

    @GetMapping("/{city}/{location}/{hotel}/book")
    public ResponseEntity<String> bookRoom(@PathVariable("hotel") final String hotelId) {
        Hotel hotel = BookingImplementation.fetchHotel(hotelId, this.getHotels());
        String bookingRefId = BookingImplementation.bookRoom(hotel);
        return new ResponseEntity<String>(bookingRefId, HttpStatus.OK);
    }


    private String getKey(String city) {
        return cityMap.keySet().stream().filter(key -> city.equals(cityMap.get(key))).findFirst().get().toString();
    }

    public static List<Hotel> getHotels() {
        return hotels;
    }

    public static void setHotels(List<Hotel> hotels) {
        BookingController.hotels = hotels;
    }
}
