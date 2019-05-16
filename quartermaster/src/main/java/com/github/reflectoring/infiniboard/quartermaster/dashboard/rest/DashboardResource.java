package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.hateoas.ResourceSupport;

public class DashboardResource extends ResourceSupport {

  private String number;

  @Pattern(regexp = "[a-z0-9-_]+", message = "must only contain characters: a-z0-9-_")
  @NotNull
  @NotEmpty
  private String slug;

  @NotNull @NotEmpty private String name;

  @NotNull @NotEmpty private String description;

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

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
