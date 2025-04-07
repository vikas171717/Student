package com.asmadiya.student.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asmadiya.student.service.DummyJsonProxyService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/proxy")
public class DummyJsonProxyController {

    private final DummyJsonProxyService proxyService;

    public DummyJsonProxyController(DummyJsonProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @GetMapping("/**")
    public ResponseEntity<String> getData(HttpServletRequest request) {
        String endpoint = extractEndpoint(request);
        return ResponseEntity.ok(proxyService.fetchData(endpoint));
    }

    @PostMapping("/**")
    public ResponseEntity<String> postData(HttpServletRequest request,
                                           @RequestBody Map<String, Object> body) {
        String endpoint = extractEndpoint(request);
        return ResponseEntity.ok(proxyService.postData(endpoint, body));
    }

    @DeleteMapping("/**")
    public ResponseEntity<String> deleteData(HttpServletRequest request) {
        String endpoint = extractEndpoint(request);
        return ResponseEntity.ok(proxyService.deleteData(endpoint));
    }

    private String extractEndpoint(HttpServletRequest request) {
        return request.getRequestURI().substring("/proxy/".length());
    }
}
