package com.asmadiya.student.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asmadiya.student.dto.ExternalUserRequest;
import com.asmadiya.student.service.ExternalApiService;

@RestController
@RequestMapping("/api")
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    public ExternalApiController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

     @GetMapping("/product")
    public ResponseEntity<String> getExternalData() {
        String data = externalApiService.fetchData();
        return ResponseEntity.ok(data);
    }

    @PostMapping("/external-login")
    public ResponseEntity<String> externalUser(@RequestBody ExternalUserRequest request) {
        String response = externalApiService.sendUserData(request.getUsername(), request.getPassword(), request.getYearOfExp());
        return ResponseEntity.ok(response);
    }
}
