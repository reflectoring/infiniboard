package com.github.reflectoring.infiniboard.packrat.dashboard;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dashboard")
public class Dashboard {

  @Id private String id;

  private String title;

  private String description;

  public Dashboard(String title) {
    this.title = title;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
