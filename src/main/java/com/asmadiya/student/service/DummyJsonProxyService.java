package com.asmadiya.student.service;

import java.util.Map;

public interface DummyJsonProxyService {
    String fetchData(String endpoint);
    String postData(String endpoint, Map<String, Object> body);
    String deleteData(String endpoint);
}
