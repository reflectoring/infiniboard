package com.github.reflectoring.infiniboard.quartermaster.info.rest;

import org.springframework.hateoas.ResourceSupport;

public class InfoResource extends ResourceSupport {

  private String version;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}
