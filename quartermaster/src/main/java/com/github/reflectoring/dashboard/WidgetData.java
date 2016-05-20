package com.github.reflectoring.dashboard;

import com.github.reflectoring.Json;

class WidgetData {

    private int dashboardId;
    private int widgetId;
    private Json data;

    public WidgetData(int dashboardId, int widgetId, Json data) {
        this.dashboardId = dashboardId;
        this.widgetId = widgetId;
        this.data = data;
    }

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
