package com.asmadiya.student.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.asmadiya.student.config.JWTProvider;
import com.asmadiya.student.dto.JwtResponse;
import com.asmadiya.student.dto.SignInRequest;
import com.asmadiya.student.dto.UserDto;
import com.asmadiya.student.entity.User;
import com.asmadiya.student.repository.UserRepository;
import com.asmadiya.student.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final JWTProvider jwtProvider ;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider ;
    }

    @Override
    public User registerUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        
        user.setRole(userDto.getRole());

        userRepository.save(user);
        
        return user;
    }

    @Override
    public Object signIn(SignInRequest signInRequest) {
        User user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow(
                () -> new RuntimeException("User not found with username: " + signInRequest.getUsername()));

        // Fetch the password by Student prn
        String password = user.getPassword();
        // Validate password
        if (!passwordEncoder.matches(signInRequest.getPassword(), password)) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT token with email as the subject
        String token = jwtProvider.generateToken(user.getEmail(), user.getRole());
        // Return JwtResponse with token and a success message

        return new JwtResponse(token, "Login successful for: " + user.getUsername());
    }
}
