package com.asmadiya.student.service;

import org.springframework.http.ResponseEntity;

public interface DummyJsonService {
    ResponseEntity<?> fetchDummyData(String dynamicEndpoint);
}
