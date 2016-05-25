package com.github.reflectoring.dashboard;

import com.github.reflectoring.Link;
import com.github.reflectoring.haljson.HalJsonResource;

import java.util.List;
import java.util.stream.Collectors;

class WidgetData {

    private int dashboardId;
    private int widgetId;
    private HalJsonResource data;

    public WidgetData(int dashboardId, int widgetId, HalJsonResource data) {
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

    public HalJsonResource getData() {
        return data;
    }

    public void setData(HalJsonResource data) {
        this.data = data;
    }

    public static HalJsonResource toResource(Dashboard dashboard, WidgetData widgetData) {
        HalJsonResource resource = new HalJsonResource();

        resource.add("dashboardId", widgetData.getDashboardId());
        resource.add("widgetId", widgetData.getWidgetId());
        resource.add("data", widgetData.getData());

        resource.add(new Link("self", "http://localhost:8090/api/dashboards/" + dashboard.getId() + "/widget-data/" + widgetData.getWidgetId()));

        return resource;
    }

    public static List<HalJsonResource> toResources(Dashboard dashboard, List<WidgetData> widgetDataList) {
        return widgetDataList.stream()
                .map(data -> WidgetData.toResource(dashboard, data))
                .collect(Collectors.toList());
    }
}
