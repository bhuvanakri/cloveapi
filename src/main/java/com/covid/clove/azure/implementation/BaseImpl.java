package com.covid.clove.azure.implementation;

import com.covid.clove.azure.models.healthmodels.domain.*;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BaseImpl {

    public static Observation fetchObservation(String code)
    {
        Bundle b = fetchRawObservations(code);
        if(b != null) {
            for (Observation o : b.getEntry()) {
                if (o.getResource().getValueQuantity() != null ||
                        o.getResource().getValueCodeableConcept() != null) {
                    return o;
                }
            }
        }
        return null;
    }

    public static List<Observation> fetchObservationList(String code)
    {
        Bundle b = fetchRawObservations(code);
        List<Observation> sortedList = new ArrayList<Observation>();
        for(Observation O: b.getEntry())
        {
            if(O.getResource().getValueQuantity() != null)
            {
                sortedList.add(O);
            }
        }
        b.setEntry(sortedList);
        return b.getEntry();
    }

    public static Bundle fetchRawObservations(String code)
    {
        String url = "http://hapi.fhir.org/baseR4/Observation?code="+code;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        String json = null;
        Bundle bundle = null;
        try {
            Response response = client.newCall(request).execute();
            json = response.body().string();
            System.out.println("##json obs :"+json);
            Gson gson = new Gson();
            bundle = gson.fromJson(json, Bundle.class);
            System.out.println("##bundle :" + bundle.getEntry());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bundle;
    }
//Patients
    private static Bundle fetchPatientsByState(String state)
    {
        String url = "http://hapi.fhir.org/baseR4/Patient?address-state="+state;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        String json = null;
        Bundle bundle = null;
        try {
            Response response = client.newCall(request).execute();
            json = response.body().string();
            Gson gson = new Gson();
            bundle = gson.fromJson(json, Bundle.class);
            System.out.println("##bundle :" + bundle.getEntry());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bundle;
    }

    public static Observation fetchPatient(String state)
    {
        Bundle b = fetchPatientsByState(state);
        for(Observation o: b.getEntry())
        {
            if(o.getResource().getName() != null)
            {
                return o;
            }
        }
        return null;
    }
    //Hospitals
    private static OrgBundle fetchHospitalsByCountry(String country)
    {
        String url = "http://hapi.fhir.org/baseR4/Organization?address-country="+country;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        String json = null;
        OrgBundle bundle = null;
        try {
            Response response = client.newCall(request).execute();
            json = response.body().string();
            System.out.println("##jsonstatehosp :" + json);
            Gson gson = new Gson();
            bundle = gson.fromJson(json, OrgBundle.class);
            System.out.println("##bundle :" + bundle.getEntry());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bundle;
    }

    public static List<OrgObservation> fetchHospitals(String country)
    {
        List<OrgObservation> hospitalsList = new ArrayList<OrgObservation>();
        OrgBundle b = fetchHospitalsByCountry(country);
        for(OrgObservation o: b.getEntry())
        {
            if(o.getResource().getTelecom() != null)
            {
            hospitalsList.add(o);}
        }
        return hospitalsList;
    }



}
