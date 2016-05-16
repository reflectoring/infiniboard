package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Map;

/**
 * configuration of a source with widget and plugin id, last modified date and a
 * time interval to be checked for new information
 */
public class SourceConfig {

    @Id
    private long id;

    private String widgetId;

    private String sourceId;

    private Date lastModified;

    private int interval;

    private Map<Integer, ConfigSource> configData;

    private boolean deleted;

    public SourceConfig(String widgetId, String sourceId, Date lastModified, int interval, Map<Integer, ConfigSource> configData) {
        this.widgetId = widgetId;
        this.sourceId = sourceId;
        this.lastModified = lastModified;
        this.interval = interval;
        this.configData = configData;
        this.deleted = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Map<Integer, ConfigSource> getConfigData() {
        return configData;
    }

    public void setConfigData(Map<Integer, ConfigSource> configData) {
        this.configData = configData;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
