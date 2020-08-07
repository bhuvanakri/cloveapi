package com.covid.clove.azure.controllers;

import java.util.Arrays;
import java.util.List;

import com.covid.clove.azure.models.bookingmodels.mockdomains.City;
import com.covid.clove.azure.models.bookingmodels.mockdomains.Hotel;
import com.covid.clove.azure.models.bookingmodels.mockdomains.HotelDetails;
import com.covid.clove.azure.models.bookingmodels.mockdomains.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PropertySource("classpath:mock.properties")
@RequestMapping("/mockbooking")
public class MockBookingController {


    @Autowired
    private Environment env;

    private List<String> cityList;
    private HotelDetails hotel;
    private List<String> locationList;
    private List<String> hotelList;


    @GetMapping("/cities")
    public ResponseEntity<City> getCities(){

        String details = env.getProperty("cities");
        String arr[] = details.split(",");
        cityList = Arrays.asList(arr);

        City city = new City();
        city.setCityList(cityList);

        return new ResponseEntity<City>(city,HttpStatus.OK);
    }


    @GetMapping("/{city}/location")
    public ResponseEntity<Location> getLocations(@PathVariable("city") final String city){

        String details = env.getProperty(city);
        String arr[] = details.split(",");
        locationList = Arrays.asList(arr);

        Location location = new Location();
        location.setLocationList(locationList);

        return new ResponseEntity<Location>(location,HttpStatus.OK);
    }

    @GetMapping("/{city}/{location}/hotel")
    public ResponseEntity<Hotel> getHotels(@PathVariable("location") final String location){

        String details = env.getProperty(location);
        String arr[] = details.split(",");
        hotelList = Arrays.asList(arr);

        Hotel hotel = new Hotel();
        hotel.setHotelList(hotelList);

        return new ResponseEntity<Hotel>(hotel,HttpStatus.OK);
    }

    @GetMapping("/{city}/{location}/{hotel}/details")
    public ResponseEntity<HotelDetails> getDetails (@PathVariable("hotel") final String hotelId){

        hotel = new HotelDetails();
        String details = env.getProperty(hotelId);
        String arr[] = details.split(",");
        hotel.setTotalRooms(Integer.parseInt(arr[0]));
        hotel.setOccuipedRooms(Integer.parseInt(arr[1]));
        hotel.setAvailableRooms(Integer.parseInt(arr[2]));
        hotel.setAddress(arr[3]);
        hotel.setAmenities(arr[4]);
        hotel.setDisplayName(arr[5]);

        return new ResponseEntity<HotelDetails>(hotel,HttpStatus.OK);

    }
}
