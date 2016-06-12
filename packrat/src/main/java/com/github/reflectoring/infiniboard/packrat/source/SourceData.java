package com.github.reflectoring.infiniboard.packrat.source;

import java.util.Map;

/**
 * contains data that was retrieved by a source
 */
public class SourceData {

    private String WidgetId;

    private String sourceId;

    private Map<String, Object> data;

    public SourceData(String widgetId, String sourceId, Map<String, Object> data) {
        WidgetId = widgetId;
        this.sourceId = sourceId;
        this.data = data;
    }

    public String getWidgetId() {
        return WidgetId;
    }

    public void setWidgetId(String widgetId) {
        WidgetId = widgetId;
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
