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
    private String id;

    private String widgetId;

    private String pluginId;

    private Date lastModified;

    private Integer interval;

    private Map<String, Object> configData;

    public SourceConfig(String widgetId, String pluginId, Date lastModified, Integer interval, Map<String, Object> configData) {
        this.widgetId = widgetId;
        this.pluginId = pluginId;
        this.lastModified = lastModified;
        this.interval = interval;
        this.configData = configData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Map<String, Object> getConfigData() {
        return configData;
    }

    public void setConfigData(Map<String, Object> configData) {
        this.configData = configData;
    }

}
