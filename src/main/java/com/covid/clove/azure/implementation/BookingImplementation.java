package com.covid.clove.azure.implementation;

import com.covid.clove.azure.mapper.BookingMapper;
import com.covid.clove.azure.models.bookingmodels.Hotel;
import com.covid.clove.azure.models.bookingmodels.LocationInfo;
import com.covid.clove.azure.models.bookingmodels.domain.HotelDomain;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingImplementation {
    static String apiKey = "hmxem4ev6tgf8zvxqyrgjvpe";
    static String Secret = "TAyGhGd8WN";

    public static List<HotelDomain> fetchHotels(String city) {
        String signature = org.apache.commons.codec.digest.DigestUtils.sha256Hex(apiKey + Secret + System.currentTimeMillis() / 1000);

        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("Api-key", apiKey);
        headerMap.put("X-Signature", signature);
        headerMap.put("Accept", "application/json");
        headerMap.put("Accept-Encoding", "gzip");
        headerMap.put("Content-Type", "application/json");
        Response response = RestAssured
                .given()
                .headers(headerMap)
                .log().all()
                .body("{\n" +
                        "  \"stay\": {\n" +
                        "    \"checkIn\": \"2020-08-20\",\n" +
                        "    \"checkOut\": \"2020-08-21\"\n" +
                        "  },\n" +
                        "  \"occupancies\": [\n" +
                        "    {\n" +
                        "      \"rooms\": 1,\n" +
                        "      \"adults\": 2,\n" +
                        "      \"children\": 0\n" +
                        "    }\n" +
                        "  ],\n" +
                        " \"destination\": {\n" +
                        "        \"code\": \"" + city + "\"\n" +
                        "    },\n" +
                        " \"country\": {\n" +
                        "        \"code\": \"IN\"\n" +
                        "    }\n" +
                        "}\n")
                .post("https://api.test.hotelbeds.com/hotel-api/1.0/hotels");
        JsonPath hotelsJsonPath = response.jsonPath();
        List<HotelDomain> hotelDomainList = BookingMapper.mapHotelDomains(response, hotelsJsonPath.getList("hotels.hotels"));
        return hotelDomainList;
    }

    public static void fetchLocation(Hotel h) {
        HttpGet request = new HttpGet("https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=" + h.getLatitude() + "&lon=" + h.getLongitude());
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse res = null;
        String address = null;
        String fulladdress = null;
        try {
            res = client.execute(request);
            HttpEntity httpEntity = res.getEntity();
            JSONObject locationObject = new JSONObject(EntityUtils.toString(httpEntity));
            fulladdress = locationObject.get("display_name").toString();
            JSONObject addrObj = new JSONObject(locationObject.get("address").toString());
            address = addrObj.get("suburb").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setDisplay_name(address);
        locationInfo.setAddress(fulladdress);
        h.setLocationInfo(locationInfo);
    }

    public static String bookRoom(Hotel hotel) {
        String signature = org.apache.commons.codec.digest.DigestUtils.sha256Hex(apiKey + Secret + System.currentTimeMillis() / 1000);
        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("Api-key", apiKey);
        headerMap.put("X-Signature", signature);
        headerMap.put("Accept", "application/json");
        headerMap.put("Accept-Encoding", "gzip");
        headerMap.put("Content-Type", "application/json");

        String rateKey = hotel.getRooms().get(0).getRates().get(0).getRateKey();
        //rateKey ="20200820|20200821|W|102|16878|DBT.IN|NRF-NO REEMBOL|RO||1~2~0||N@03~~20032~1019975225~S~407271977A0748B159662136201500AAUK000038400000000012262f";
        Response response = RestAssured
                .given()
                .headers(headerMap)
                .log().all()
                .body("{\r\n" +
                        "    \"holder\": {\r\n" +
                        "        \"name\": \"Georgina\",\r\n" +
                        "        \"surname\": \"Priest\"\r\n" +
                        "    },\r\n" +
                        "    \"rooms\": [\r\n" +
                        "        {\r\n" +
                        "            \"rateKey\":\"" + rateKey + "\",\r\n" +
                        "            \"paxes\": [\r\n" +
                        "                {\r\n" +
                        "                    \"roomId\": \"1\",\r\n" +
                        "                    \"type\": \"AD\",\r\n" +
                        "                    \"name\": \"Georgina\",\r\n" +
                        "                    \"surname\": \"Priest\"\r\n" +
                        "                }\r\n" +
                        "            ]\r\n" +
                        "        }\r\n" +
                        "    ],\r\n" +
                        "    \"clientReference\": \"IntegrationAgency\",\r\n" +
                        "    \"remark\": \"Booking remarks are to be written here.\",\r\n" +
                        "	\"tolerance\" : 9.00\r\n" +
                        "    }")
                .post("https://api.test.hotelbeds.com/hotel-api/1.0/bookings");

        JsonPath bookingJsonPath = response.jsonPath();
        String bookingRef = bookingJsonPath.get("booking.reference");
        return bookingRef;
    }

    public static Hotel fetchHotel(String hotelName, List<Hotel> hotels) {
        for (Hotel htl : hotels) {
            if (htl.getName().equalsIgnoreCase(hotelName)) {
                return htl;
            }
        }
        return null;
    }
}
