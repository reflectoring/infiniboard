package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * configuration of a source with sourceConfig and plugin id, last modified date and a
 * time interval to be checked for new information
 */
public class SourceConfig {

    @Id
    private String id;

    private String widgetId;

    private Date lastModified;

    private List<UrlSource> urlSources;

    private boolean deleted;

    public SourceConfig(String id, String widgetId, Date lastModified) {
        this.id = id;
        this.widgetId = widgetId;
        this.lastModified = lastModified;
        this.urlSources = new ArrayList<>();
        this.deleted = false;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
