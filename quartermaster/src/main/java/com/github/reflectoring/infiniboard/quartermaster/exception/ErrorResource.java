package com.github.reflectoring.infiniboard.quartermaster.exception;

public class ErrorResource {
  String message;
  int status;

  public ErrorResource(String message, int status) {
    this.message = message;
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
