package com.telco.venta.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private Integer status;
    private Instant timestamp;
    private List<String> invalidParameters;

    public ErrorResponse(String code, String message, Integer status, Instant timestamp, List<String> invalidParameters) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.invalidParameters = invalidParameters;
    }
}
