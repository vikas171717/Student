package com.asmadiya.student.config;


import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // Log the token contents
            logger.debug("Token contents: " + token);
            String email = jwtProvider.getUsernameFromToken(token);
            String role = jwtProvider.extractRole(token);

            // Log the extracted role and email
            logger.debug("Extracted email: " + email + ", Extracted role: " + role);

            if (email == null || role == null || role.isEmpty()) {
                logger.warn("Token is invalid: Missing email or role");
                filterChain.doFilter(request, response);
                return;
            }
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // Validate token without needing user details
                if (jwtProvider.validateToken(token, email)) {
                    // Create an authentication token with the extracted role
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            email, null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    logger.error("Invalid token for email: " + email);
                }
            } else {
                logger.warn("Invalid token or missing role/email for token: " + token);
            }
        }
        filterChain.doFilter(request, response);
    }
}
