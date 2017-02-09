package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.hateoas.ResourceSupport;

public class WidgetConfigResource extends ResourceSupport {

  @NotNull private String title;

  private String type;

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  private String titleUrl;

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  private String description;

  private Date lastModified;

  private List<SourceConfig> sourceConfigs = new ArrayList<>();

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

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public List<SourceConfig> getSourceConfigs() {
    return sourceConfigs;
  }

  public void setSourceConfigs(List<SourceConfig> sourceConfigs) {
    this.sourceConfigs = sourceConfigs;
  }
}
