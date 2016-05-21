package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * configuration of a source with sourceConfig and plugin id, last modified date and a
 * time interval to be checked for new information
 */
public class SourceConfig {

    @Id
    private int id;

    private String widgetId;

    private Date lastModified;

    private List<UrlSource> urlSources;

    public SourceConfig(String widgetId, Date lastModified) {
        this.widgetId = widgetId;
        this.lastModified = lastModified;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<UrlSource> getUrlSources() {
        return urlSources;
    }

    public void setUrlSources(List<UrlSource> sources) {
        this.urlSources = sources;
    }
}
