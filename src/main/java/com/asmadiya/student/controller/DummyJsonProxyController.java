package com.asmadiya.student.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/**")
    public ResponseEntity<String> getData(HttpServletRequest request) {
        String token = extractToken(request);
        String endpoint = extractEndpoint(request);
        return ResponseEntity.ok(proxyService.fetchDataWithAuth(endpoint, token));
    }

    @PostMapping("/**")
    public ResponseEntity<String> postData(HttpServletRequest request,
                                           @RequestBody Map<String, Object> body) {
        String endpoint = extractEndpoint(request);
        return ResponseEntity.ok(proxyService.postData(endpoint, body));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/**")
    public ResponseEntity<String> deleteData(HttpServletRequest request) {
        String token = extractToken(request);
        String endpoint = extractEndpoint(request);
        return ResponseEntity.ok(proxyService.deleteDataWithAuth(endpoint, token));
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  
        }
        return null;
    }
    
    private String extractEndpoint(HttpServletRequest request) {
        return request.getRequestURI().substring("/proxy/".length());
    }
}
