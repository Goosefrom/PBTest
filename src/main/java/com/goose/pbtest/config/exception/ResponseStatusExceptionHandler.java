package com.goose.pbtest.config.exception;

import com.sun.net.httpserver.Headers;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResponseStatusExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FindingException.class)
    protected ResponseEntity<Object> handleCustomException(FindingException e) {
        e.printStackTrace();
        return ResponseEntity.status(e.getErrorType().getHttpError())
                .contentType(MediaType.APPLICATION_JSON)
                .body(e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ex.printStackTrace();
        List<String> validation = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(validation.toString());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleRuntimeException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .body(e.getMessage() == null || e.getMessage().isBlank() ? "Internal error" : e.getMessage());
    }
}
