package com.github.reflectoring.dashboard;

import com.github.reflectoring.haljson.Json;

public class WidgetDataResource {

    private int dashboardId;
    private int widgetId;
    private Json data;

    public int getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(int dashboardId) {
        this.dashboardId = dashboardId;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public Json getData() {
        return data;
    }

    public void setData(Json data) {
        this.data = data;
    }
}
