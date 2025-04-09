package com.asmadiya.student.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.asmadiya.student.dto.ProductDto;
import com.asmadiya.student.dto.ProductListResponse;
import com.asmadiya.student.entity.Product;
import com.asmadiya.student.repository.ProductRepository;
import com.asmadiya.student.service.DummyJsonProxyService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/proxy")
public class DummyJsonProxyController {

    private final DummyJsonProxyService proxyService;
    @Autowired
    private ProductRepository productRepository;

    public DummyJsonProxyController(DummyJsonProxyService proxyService, ProductRepository productRepository) {
        this.proxyService = proxyService;
        this.productRepository = productRepository;
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

    @GetMapping("/products")
    public ResponseEntity<String> fetchAndSaveProducts() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://dummyjson.com/products";

        ResponseEntity<ProductListResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProductListResponse>() {
                });

        List<ProductDto> apiProducts = response.getBody().getProducts();

        List<Product> products = apiProducts.stream().map(dto -> {
            Product p = new Product();
            p.setId(dto.getId()); 
            p.setCategory(dto.getCategory());
            p.setDescription(dto.getDescription());
            p.setPrice(dto.getPrice());
            p.setTitle(dto.getTitle());
            return p;
        }).collect(Collectors.toList());
        System.out.println("Fetching and saving products... Total: " + products.size());
        productRepository.saveAll(products);

        return ResponseEntity.ok("Products saved successfully!");
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
