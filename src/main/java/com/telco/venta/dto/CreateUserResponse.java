package com.telco.venta.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserResponse {

    private Long id;
    private String username;
    private String role;
    private Integer supervisorId;
    private Boolean activo;
    private String message;
}