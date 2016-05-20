package com.github.reflectoring.dashboard;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

class DashboardResource extends ResourceSupport {

    private int dashboardId;
    private String name;
    private String description;

    private List<WidgetConfigResource> widgetConfigs;

    public int getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(int dashboardId) {
        this.dashboardId = dashboardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WidgetConfigResource> getWidgetConfigs() {
        return widgetConfigs;
    }

    public void setWidgetConfigs(List<WidgetConfigResource> widgetConfigs) {
        this.widgetConfigs = widgetConfigs;
    }
}
