package com.asmadiya.student.serviceImpl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.asmadiya.student.dto.ExternalUserRequest;
import com.asmadiya.student.service.ExternalApiService;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {
    private final RestTemplate restTemplate;

    public ExternalApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String fetchData() {
        String url = "https://dummyjson.com/products";
        return restTemplate.getForObject(url, String.class);
    }

    @Override
    public String sendUserData(String username, String password, int yearOfExp) {
        String url = "https://dummyjson.com/auth/login";

        ExternalUserRequest externalUserRequest = new ExternalUserRequest(username, password, yearOfExp);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ExternalUserRequest> requestEntity = new HttpEntity<>(externalUserRequest, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        return response.getBody();
    }

}
