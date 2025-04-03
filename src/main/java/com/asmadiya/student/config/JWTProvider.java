package com.asmadiya.student.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    // Generate token
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) // Set email as the subject (user identifier)
                .claim("role", role.toUpperCase()) // Add role as a claim
                .setIssuedAt(new Date()) // Set the issue date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Set the expiration (1 hour)
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes(StandardCharsets.UTF_8)) // Sign the token with
                                                                                                // your secret key
                .compact(); // Return the compacted JWT token
    }

    // Extract email from token
    public String getUsernameFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    // Validate token
    public boolean validateToken(String token, String email) {
        try {
            return email.equals(getUsernameFromToken(token)) && !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            return false; // Token is expired
        } catch (Exception e) {
            return false; // Some other error
        }
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Private helper methods
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)) // Use getBytes()
                .build() // Must call build() in newer versions
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractRole(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)) // Use your secret key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Extract the role from the claims and return it, ensuring it's a String
            return claims.get("role", String.class);
        } catch (JwtException | IllegalArgumentException e) {
            // Log the exception or handle accordingly
            return null; // Return null if there's an error in parsing the token or extracting the claim
        }
    }
}