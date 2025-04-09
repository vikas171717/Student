package com.asmadiya.student.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String category;
    private String description;
    private Double price;
    private String title;
}

