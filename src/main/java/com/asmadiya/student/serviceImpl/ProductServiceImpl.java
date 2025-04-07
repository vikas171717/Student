package com.asmadiya.student.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.asmadiya.student.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final String BASE_URL = "https://dummyjson.com/";
    private final RestTemplate restTemplate;

    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String fetchDummyData(String path) {
        String fullUrl = BASE_URL + path;
        return restTemplate.getForObject(fullUrl, String.class);
    }

    @Override
    public String postDummyData(String endpoint, Object data) {
        String url = BASE_URL + endpoint;
        return restTemplate.postForObject(url, data, String.class);
    }

    @Override
    public String deleteProductById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "products/" + id;
        restTemplate.delete(url);
        return "Product with ID " + id + " deleted successfully.";
    }

}
