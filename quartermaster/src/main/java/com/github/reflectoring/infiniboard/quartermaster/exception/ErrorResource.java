package com.github.reflectoring.infiniboard.quartermaster.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class ErrorResource extends ResourceSupport {
  List<String> errors = new ArrayList<>();

  protected ErrorResource() {}

  public ErrorResource(Errors errors) {
    for (FieldError error : errors.getFieldErrors()) {
      this.errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
  }

  public ErrorResource(String errors) {
    this.errors.add(errors);
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }

  @JsonIgnore
  public List<Link> getLinks() {
    // suppress rendering of links attribute in error messages
    return null;
  }
}
