package com.asmadiya.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asmadiya.student.dto.SignInRequest;
import com.asmadiya.student.dto.UserDto;
import com.asmadiya.student.entity.User;
import com.asmadiya.student.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService ;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        User createdUser = userService.registerUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        Object response = userService.signIn(signInRequest);
        return ResponseEntity.ok(response);
    }

}
