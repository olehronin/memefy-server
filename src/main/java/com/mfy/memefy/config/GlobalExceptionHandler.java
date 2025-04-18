package com.mfy.memefy.config;

import com.mfy.memefy.domain.Response;
import com.mfy.memefy.utils.RequestUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;

/**
 * The {@link GlobalExceptionHandler} class
 *
 * @author Oleh Ivasiuk
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGenericException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request));
    }

    private Response buildResponse(HttpStatus status, String message, WebRequest request) {
        return RequestUtils.getResponse(
                ((ServletWebRequest) request).getRequest(),
                Collections.emptyMap(),
                message,
                status
        );
    }
}
