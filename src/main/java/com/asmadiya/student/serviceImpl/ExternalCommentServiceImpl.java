package com.asmadiya.student.serviceImpl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.asmadiya.student.dto.ExternalCommentsDto;
import com.asmadiya.student.service.ExternalCommentService;

@Service
public class ExternalCommentServiceImpl implements ExternalCommentService {
    private final RestTemplate restTemplate;

    public ExternalCommentServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String sendCommentData(String dynamicPath, ExternalCommentsDto requestBody) {
        String baseUrl = "https://dummyjson.com/comments";

        if (dynamicPath != null && !dynamicPath.isEmpty()) {
            baseUrl += "/" + dynamicPath;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ExternalCommentsDto> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, requestEntity, String.class);

        return response.getBody();
    }

    @Override
    public String fetchCommentData(String query) {
        String baseUrl = "https://dummyjson.com/comments";

        if (query != null && !query.isEmpty()) {
            baseUrl += "?" + query; 
        }

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
        return response.getBody();
    }

    @Override
    public String updateComment(int id, ExternalCommentsDto requestBody) {
        String url = "https://dummyjson.com/comments/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ExternalCommentsDto> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                String.class);

        return response.getBody();
    }

    @Override
    public String deleteCommentById(int id) {
        String url = "https://dummyjson.com/comments/" + id;
    
        restTemplate.delete(url);
        return "Comment with ID " + id + " deleted successfully.";
    }

    @Override
    public String getCommentById(int id) {
        String url = "https://dummyjson.com/comments/" + id;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
    
    
}
