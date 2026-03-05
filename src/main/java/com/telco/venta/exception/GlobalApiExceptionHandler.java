package com.telco.venta.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Slf4j
public class GlobalApiExceptionHandler extends ResponseEntityExceptionHandler {
        private final Clock clock = Clock.systemUTC();

        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
                ErrorResponse errorResponse = new ErrorResponse(
                                exception.getCode(),
                                exception.getMessage(),
                                exception.getHttpStatus().value(),
                                Instant.now(clock),
                                null

                );
                return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
        }

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(
                         @NonNull MethodArgumentNotValidException ex,
                         @NonNull HttpHeaders headers,
                         @NonNull HttpStatusCode status,
                         @NonNull WebRequest request) {

                List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .toList();

                ErrorResponse errorResponse = new ErrorResponse(
                                "VALIDATION_ERROR",
                                "Errores de validación",
                                HttpStatus.BAD_REQUEST.value(),
                                Instant.now(clock),
                                errors);



                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception) {
                ErrorResponse errorResponse = new ErrorResponse(
                                "INTERNAL SERVER ERROR",
                                "Ocurrio un error inesperado",
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                Instant.now(clock),
                                null);
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
