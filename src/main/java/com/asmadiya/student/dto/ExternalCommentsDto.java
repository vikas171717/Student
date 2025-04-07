package com.asmadiya.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalCommentsDto {
    private String body;
    private int postId;
    private int userId;
}
