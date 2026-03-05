package com.telco.venta.exception.enums;


import com.telco.venta.exception.policy.BusinessExceptionPolicy;
import org.springframework.http.HttpStatus;

public enum BusinessExceptionReason implements BusinessExceptionPolicy {

    ENTITY_NOT_FOUND("%s, no existe",HttpStatus.NOT_FOUND),
    ENTITY_CONFICT("%s",HttpStatus.CONFLICT),
    ENTITY_EXITS("%s ya existe ",HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    BusinessExceptionReason(String message, HttpStatus httpStatus) {
        this.code = BusinessExceptionReason.class.getSimpleName();
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}