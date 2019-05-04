package com.github.reflectoring.infiniboard.quartermaster.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {Exception.class, RuntimeException.class})
  protected ResponseEntity<Object> handleUncaughtExceptions() {
    String message = "An internal server error occurred.";

    return new ResponseEntity<>(
        new ErrorResource(message), new HttpHeaders(), INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {ResourceAlreadyExistsException.class})
  protected ResponseEntity<ErrorResource> handleResourceAlreadyExists(
      ResourceAlreadyExistsException ex) {
    String message = "Resource already exists";

    return new ResponseEntity<>(new ErrorResource(message), new HttpHeaders(), BAD_REQUEST);
  }
}
