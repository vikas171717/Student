package com.asmadiya.student.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.asmadiya.student.config.JwtAuthenticationFilter;

@Configuration // Marks this class as a configuration class
@EnableWebSecurity // Enables Spring Security for the application
public class SecurityConfig {

    @Autowired // Injects JwtAuthenticationFilter
    private JwtAuthenticationFilter jwtAuthorizationFilter;

    // Bean for password encoding using BCrypt
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configures security settings for the application
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disables CSRF protection as JWT is used
                .csrf(csrf -> csrf.disable())

                // Configures authorization rules for different API endpoints
                .authorizeHttpRequests(authz -> authz
                        // Publicly accessible endpoints
                        .requestMatchers("api/**")
                        .permitAll()

                        //Product
                        .requestMatchers("/api/products","/api/products/**","/api/products/search","/api/products/add","/api/products/**")
                        .permitAll()

                        // comments
                        .requestMatchers("api/comments","api/comments/**")
                        .permitAll()
                        .requestMatchers("/api/product","/api/external-login")
                        .permitAll()
                        .requestMatchers("/api/user/register",
                        "/api/user/signin","/error")
                        .permitAll()
                        .requestMatchers("/api/student/top/{state}",
                        "/api/student/topper")
                        .hasAnyRole("FACULTY", "STUDENT", "ADMIN")
                        .requestMatchers("/api/student/get-all-students").hasAnyRole("PRINCIPAL", "FACULTY", "ADMIN")
                        .requestMatchers("/api/excel/upload")
                        .hasAnyRole("ADMIN", "FACULTY")
                        .anyRequest().authenticated())

                // Sets session management policy to stateless (JWT-based authentication)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Adds JWT filter before Spring Security's authentication filter
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configures CORS settings
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*") 
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
