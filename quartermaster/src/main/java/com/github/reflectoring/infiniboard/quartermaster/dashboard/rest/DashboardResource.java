package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import javax.validation.constraints.NotNull;
import org.springframework.hateoas.ResourceSupport;

public class DashboardResource extends ResourceSupport {

  private String number;

  @NotNull private String name;

  @NotNull private String description;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
