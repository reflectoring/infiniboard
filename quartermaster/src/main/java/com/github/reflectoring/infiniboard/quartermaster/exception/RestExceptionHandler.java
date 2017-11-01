package com.github.reflectoring.infiniboard.quartermaster.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {Exception.class, RuntimeException.class})
  protected ResponseEntity<Object> handleUncaughtExceptions(Exception ex, WebRequest request) {
    String message = "An internal server error occurred.";

    HttpStatus status = INTERNAL_SERVER_ERROR;
    return new ResponseEntity<>(
        new ErrorResource(message, status.value()), new HttpHeaders(), status);
  }
}
