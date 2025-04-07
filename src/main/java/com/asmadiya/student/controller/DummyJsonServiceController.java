package com.asmadiya.student.controller;

import org.springframework.web.bind.annotation.RestController;

import com.asmadiya.student.service.DummyJsonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("api")
public class DummyJsonServiceController {
    @Autowired
    private DummyJsonService dummyJsonService;

    @GetMapping("/{dynamicEndpoint}")
    public ResponseEntity<?> getDynamicData(@PathVariable String dynamicEndpoint) {
        return dummyJsonService.fetchDummyData(dynamicEndpoint);

    }
}