package com.asmadiya.student.dto;

import java.util.List;

public class ProductListResponse {
    private List<ProductDto> products;

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}


