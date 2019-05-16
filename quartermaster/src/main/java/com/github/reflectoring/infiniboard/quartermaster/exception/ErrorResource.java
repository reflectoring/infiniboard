package com.github.reflectoring.infiniboard.quartermaster.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class ErrorResource extends ResourceSupport {
  Map<String, String> errors = new HashMap<>();

  protected ErrorResource() {}

  public ErrorResource(Errors errors) {
    for (FieldError error : errors.getFieldErrors()) {
      this.errors.put(error.getField(), error.getDefaultMessage());
    }
  }

  public ErrorResource(String errors) {
    this.errors.put("exception", errors);
  }

  public Map<String, String> getErrors() {
    return errors;
  }

  public void setErrors(Map<String, String> errors) {
    this.errors = errors;
  }

  @JsonIgnore
  public List<Link> getLinks() {
    // suppress rendering of links attribute in error messages
    return null;
  }
}
