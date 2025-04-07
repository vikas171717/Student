package com.asmadiya.student.service;

public interface ProductService {
    String fetchDummyData(String path);

    String postDummyData(String endpoint, Object data);

    String deleteProductById(Long id);

}
