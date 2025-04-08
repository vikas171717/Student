package com.asmadiya.student.service;

import java.util.Map;

public interface DummyJsonProxyService {
    String fetchData(String endpoint);
    String postData(String endpoint, Map<String, Object> body);
    String deleteData(String endpoint);
    String fetchDataWithAuth(String endpoint, String token);
    String deleteDataWithAuth(String endpoint, String token);
}
