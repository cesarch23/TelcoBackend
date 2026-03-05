package com.telco.venta.Controller;

import com.telco.venta.dto.CreateUserRequest;
import com.telco.venta.dto.CreateUserResponse;
import com.telco.venta.dto.LoginRequest;
import com.telco.venta.dto.LoginResponse;
import com.telco.venta.serviceImpl.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/v1/auth")
public class AuthController {
    private final AuthServiceImpl authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthServiceImpl authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            LoginResponse response = authService.generateToken(authentication);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException ex) {
            // Retorna respuesta de error con estado 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.builder()
                            .accessToken(null)
                            .type("Error")
                            .build());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse response = authService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
