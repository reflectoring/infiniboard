package com.github.reflectoring.infiniboard.packrat.source;

import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "data")
public class SourceData {

  @Id private String id;

  private String widgetId;

  private String sourceId;

  private Map<String, Object> data;

  public SourceData() {}

  public SourceData(String widgetId, String sourceId, Map<String, Object> data) {
    this.widgetId = widgetId;
    this.sourceId = sourceId;
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public String getWidgetId() {
    return widgetId;
  }

  public void setWidgetId(String widgetId) {
    this.widgetId = widgetId;
  }

  public String getSourceId() {
    return sourceId;
  }

  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }
}
