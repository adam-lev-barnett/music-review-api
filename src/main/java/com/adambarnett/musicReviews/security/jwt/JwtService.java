package com.adambarnett.musicReviews.security.jwt;

import com.adambarnett.musicReviews.exception.InvalidArgumentException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    /** Used to sign and validate tokens*/
    private static final String JWT_SECRET = "uii3w8croyn8&876^&TGYWHD)*(87HIYUGUEuiysdh%&*(^gka';,";

    //& Token expiration time as a constant so it's easily adjustable
    private static final long JWT_EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    public String generateJwtToken(String username) {
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME);
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    // Strip down the full header
    public String extractUsername(String header) throws InvalidArgumentException {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new InvalidArgumentException("Invalid authorization header");
        }
        // Removes "Bearer " from the String
        String token = header.substring(7);

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new InvalidArgumentException("Invalid or expired token");
        }
    }

    public boolean isTokenValid(String header, UserDetails userDetails) throws InvalidArgumentException {
        String username = extractUsername(header);
        return username.equals(userDetails.getUsername());
    }
}
