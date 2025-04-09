package com.asmadiya.student.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.asmadiya.student.dto.ProductDto;
import com.asmadiya.student.dto.ProductListResponse;
import com.asmadiya.student.entity.Product;
import com.asmadiya.student.repository.ProductRepository;
import com.asmadiya.student.service.ProductSchedulerService;

@Service
public class ProductSchedulerServiceImpl implements ProductSchedulerService {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    public ProductSchedulerServiceImpl(ProductRepository productRepository, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.restTemplate = new RestTemplate();
    }

    @Override
    @Scheduled(cron = "0 27 12 * * *")
    public void fetchAndStoreProducts() {
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
            p.setTitle(dto.getTitle());
            p.setDescription(dto.getDescription());
            p.setPrice(dto.getPrice());
            p.setCategory(dto.getCategory());
            return p;
        }).collect(Collectors.toList());

        productRepository.saveAll(products);
        System.out.println("Products fetched and stored by scheduler.");
    }

}
