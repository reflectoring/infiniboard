package com.github.reflectoring.infiniboard.packrat.widget;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "widget")
public class WidgetConfig {

  @Id private String id;

  private String title;

  private String type;

  private String titleUrl;

  private String description;

  private String dashboardId;

  private LocalDateTime lastModified = LocalDateTime.now();

  private List<SourceConfig> sourceConfigs = new ArrayList<>();

  public WidgetConfig() {}

  public WidgetConfig(String title) {
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTitleUrl() {
    return titleUrl;
  }

  public void setTitleUrl(String url) {
    this.titleUrl = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDashboardId() {
    return dashboardId;
  }

  public void setDashboardId(String dashboardId) {
    this.dashboardId = dashboardId;
  }

  public LocalDateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(LocalDateTime lastModified) {
    this.lastModified = lastModified;
  }

  public List<SourceConfig> getSourceConfigs() {
    return sourceConfigs;
  }

  public void setSourceConfigs(List<SourceConfig> sourceConfigs) {
    this.sourceConfigs = sourceConfigs;
  }

  public int size() {
    return sourceConfigs.size();
  }

  public boolean isEmpty() {
    return sourceConfigs.isEmpty();
  }

  public boolean contains(Object o) {
    return sourceConfigs.contains(o);
  }

  public boolean add(SourceConfig sourceConfig) {
    return sourceConfigs.add(sourceConfig);
  }

  public boolean remove(Object o) {
    return sourceConfigs.remove(o);
  }

  public void clear() {
    sourceConfigs.clear();
  }
}
