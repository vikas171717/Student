package com.asmadiya.student.service;

public interface ExternalApiService {
    String fetchData();
    String sendUserData(String username, String password, int yearOfExp);
}
