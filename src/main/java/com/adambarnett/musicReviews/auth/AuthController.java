package com.adambarnett.musicReviews.auth;

import com.adambarnett.musicReviews.auth.dto.LoginRequestDTO;
import com.adambarnett.musicReviews.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("login")
class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {

        // Throws exception if authentication unsuccessful
        authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()));

        String token = jwtService.generateJwtToken(loginRequest.username());

        return ResponseEntity.ok(token);

    }
}
