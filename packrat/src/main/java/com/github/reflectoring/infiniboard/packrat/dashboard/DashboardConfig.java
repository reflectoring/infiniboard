package com.github.reflectoring.infiniboard.packrat.dashboard;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;

public class DashboardConfig {

    @Id
    private String id;

    private String title;

    private String description;

    private List<WidgetConfig> widgetConfigs;

    public DashboardConfig() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WidgetConfig> getWidgetConfigs() {
        return widgetConfigs;
    }

    public void setWidgetConfigs(List<WidgetConfig> widgetConfigs) {
        this.widgetConfigs = widgetConfigs;
    }
}
