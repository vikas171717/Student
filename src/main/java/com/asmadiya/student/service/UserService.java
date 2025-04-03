package com.asmadiya.student.service;

import com.asmadiya.student.dto.SignInRequest;
import com.asmadiya.student.dto.UserDto;
import com.asmadiya.student.entity.User;

public interface UserService {
    User registerUser(UserDto userDto);

    Object signIn(SignInRequest signInRequest);

}
