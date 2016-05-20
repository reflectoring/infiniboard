package com.github.reflectoring.dashboard;

import org.springframework.hateoas.ResourceSupport;

public class WidgetConfigResource extends ResourceSupport {

    private int widgetId;
    private String type;
    private int interval;

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
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
}
