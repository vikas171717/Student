package com.asmadiya.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalUserRequest {
    private String username;
    private String password;
    private int yearOfExp;
}
