package com.telco.venta.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Username o email debe es requerido")
    private String username;

    @NotBlank(message = "Password es requerido")
    private String password;
}
