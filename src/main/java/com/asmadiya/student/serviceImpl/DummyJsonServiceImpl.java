package com.asmadiya.student.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.asmadiya.student.service.DummyJsonService;

@Service
public class DummyJsonServiceImpl implements DummyJsonService {
    private final String BASE_URL = "https://dummyjson.com/";

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ResponseEntity<?> fetchDummyData(String dynamicEndpoint) {
        String fullUrl = BASE_URL + dynamicEndpoint;
         try {
            ResponseEntity<String> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            return response;
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid endpoint or error fetching data: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
