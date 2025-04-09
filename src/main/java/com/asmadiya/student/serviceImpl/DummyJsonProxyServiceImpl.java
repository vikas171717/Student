package com.asmadiya.student.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.asmadiya.student.dto.ProductDto;
import com.asmadiya.student.dto.ProductListResponse;
import com.asmadiya.student.entity.Product;
import com.asmadiya.student.repository.ProductRepository;
import com.asmadiya.student.service.DummyJsonProxyService;

@Service
public class DummyJsonProxyServiceImpl implements DummyJsonProxyService {

    private final String BASE_URL = "https://dummyjson.com/";

    @Autowired
    @Qualifier("dummyJsonRestTemplate")
    private final RestTemplate restTemplate;

    public DummyJsonProxyServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Autowired
    private ProductRepository productRepository;

    @Override
    public String fetchData(String endpoint) {
        String url = BASE_URL + endpoint;

        if (endpoint.equalsIgnoreCase("products")) {
            ResponseEntity<ProductListResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ProductListResponse>() {
                    });

            List<ProductDto> productDtos = response.getBody().getProducts();

            if (productDtos != null && !productDtos.isEmpty()) {
                List<Product> products = productDtos.stream().map(dto -> {
                    Product p = new Product();
                    p.setId(dto.getId());
                    p.setTitle(dto.getTitle()); 
                    p.setDescription(dto.getDescription());
                    p.setPrice(dto.getPrice());
                    return p;
                }).collect(Collectors.toList());

                productRepository.saveAll(products); 
            }
        }
        return restTemplate.getForObject(url, String.class);
    }

    @Override
    public String postData(String endpoint, Map<String, Object> body) {
        String url = BASE_URL + endpoint;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(url, request, String.class);
    }

    @Override
    public String deleteData(String endpoint) {
        String url = BASE_URL + endpoint;
        restTemplate.delete(url);
        return "Deleted successfully from: " + endpoint;
    }

    @Override
    public String fetchDataWithAuth(String endpoint, String token) {
        String url = "https://dummyjson.com/" + endpoint;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    @Override
    public String deleteDataWithAuth(String endpoint, String token) {
        String url = "https://dummyjson.com/" + endpoint;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);

        return response.getBody();
    }
}
