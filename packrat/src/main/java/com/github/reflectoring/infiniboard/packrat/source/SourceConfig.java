package com.github.reflectoring.infiniboard.packrat.source;

import java.util.Map;

/**
 * configuration of a source with widget and plugin id, last modified date and a time interval to be checked for new information
 */
public class SourceConfig {

    private String id;

    private String type;

    private int interval;

    private Map<String, Object> configData;

    public SourceConfig() {
    }

    public SourceConfig(String id, String type, int interval, Map<String, Object> configData) {
        this.id = id;
        this.type = type;
        this.interval = interval;
        this.configData = configData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Map<String, Object> getConfigData() {
        return configData;
    }

    public void setConfigData(Map<String, Object> configData) {
        this.configData = configData;
    }

}
