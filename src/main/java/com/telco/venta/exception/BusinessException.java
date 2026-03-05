package com.telco.venta.exception;


import com.telco.venta.exception.enums.BusinessExceptionReason;
import com.telco.venta.exception.policy.BusinessExceptionPolicy;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException implements BusinessExceptionPolicy {

    protected final String code;
    protected final String message;
    protected final HttpStatus httpStatus;
    public BusinessException(final BusinessExceptionReason reason){
        this.code = reason.getCode();
        this.message = reason.getMessage();
        this.httpStatus = reason.getHttpStatus();
    }
    public BusinessException(final BusinessExceptionReason reason, final HttpStatus overrindingHttpStatus){
        this.code = reason.getCode();
        this.message = reason.getMessage();
        this.httpStatus = overrindingHttpStatus;
    }
    public BusinessException(final BusinessExceptionReason reason, final Object... parameters){

        if(parameters !=null){
            this.message = String.format(reason.getMessage(),parameters);
        }else{
            this.message = reason.getMessage();
        }
        this.code = reason.getCode();
        this.httpStatus = reason.getHttpStatus();
    }
    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    public String toString() {
        return String.format(
                "BusinessException(code=%s, message=%s, httpStatus=%s)",
                this.getCode(), this.getMessage(),
                this.getHttpStatus().value()
        );
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
