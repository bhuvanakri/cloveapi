package com.covid.clove.azure.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.covid.clove.azure.models.bookingmodels.mockdomains.Location;
import com.covid.clove.azure.models.volunteermodels.Volunteer;
import com.covid.clove.azure.models.volunteermodels.VolunteerList;
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
@PropertySource("classpath:volunteer.properties")
@RequestMapping("/volunteer")
public class VolunteerController {
	
	@Autowired
	 private Environment env;
	
	private List<String> locationList;
	
	
	
	private Volunteer volunteer;
	
		@GetMapping("/locations")
	 public ResponseEntity<Location> getLocations(){
		 
		 String details = env.getProperty("locations");
		 String arr[] = details.split(",");
		 locationList = Arrays.asList(arr);
		 
		 Location location = new Location();
		 location.setLocationList(locationList);		 
		 

		 return new ResponseEntity<Location>(location,HttpStatus.OK);
	 }
	
	@GetMapping("/{location}/getList")
	 public ResponseEntity<VolunteerList> getVolunteers(@PathVariable("location") final String location){
		 List<Volunteer> tempList = new ArrayList<Volunteer>();
		 
		 String details = env.getProperty(location);
		 String arr[] = details.split("::");
		 List<String> dummyList = Arrays.asList(arr);
		 
		 for(String test : dummyList) {
			 String in[] = test.split(",");
			 volunteer = new Volunteer();
			 volunteer.setName(in[0]);
			 volunteer.setAddress(in[1]);
			 volunteer.setService(in[2]);
			 volunteer.setPhoneNumber(in[3]);
			 volunteer.setMailId(in[4]);
			 tempList.add(volunteer);
		 }
		 
		 VolunteerList volunteerList = new VolunteerList();
		 volunteerList.setList(tempList);
				 
		 
		 return new ResponseEntity<VolunteerList>(volunteerList,HttpStatus.OK);
	 }

 

}
