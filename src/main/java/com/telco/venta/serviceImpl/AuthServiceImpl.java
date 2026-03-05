package com.telco.venta.serviceImpl;

import com.telco.venta.dto.CreateUserRequest;
import com.telco.venta.dto.CreateUserResponse;
import com.telco.venta.dto.LoginResponse;
import com.telco.venta.entity.User;
import com.telco.venta.security.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {
    private final JwtTokenProvider tokenProvider;
    private final UserServiceImpl userService;

    public AuthServiceImpl(JwtTokenProvider tokenProvider, UserServiceImpl userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    public LoginResponse generateToken(Authentication authentication) {
        String token = tokenProvider.generateToken(authentication);

        return LoginResponse.builder()
                .accessToken(token)
                .type("Bearer")
                .build();
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = userService.createUser(
                request.getUsername(),
                request.getPassword(),
                request.getRole(),
                request.getSupervisorId()
            );

        return CreateUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().toString())
                .supervisorId(user.getSupervisorId() != null ? user.getSupervisorId().intValue() : null)
                .activo(user.getActivo())
                .message("El usuario con el user '" + user.getUsername() + "' fue creado exitosamente")
                .build();
    }
}
