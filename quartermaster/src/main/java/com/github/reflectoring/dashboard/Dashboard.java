package com.github.reflectoring.dashboard;

import com.github.reflectoring.Link;
import com.github.reflectoring.haljson.HalJsonResource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dashboard {

    private int id;
    private String name;
    private String description;

    private List<WidgetConfig> widgetConfigs;

    public Dashboard(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.widgetConfigs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<WidgetConfig> getWidgetConfigs() {
        return widgetConfigs;
    }

    public void setWidgetConfigs(List<WidgetConfig> widgetConfigs) {
        this.widgetConfigs = widgetConfigs;
    }

    public static HalJsonResource toResource(Dashboard dashboard) {
        HalJsonResource resource = new HalJsonResource();
        resource.add("id", dashboard.getId());
        resource.add("name", dashboard.getName());
        resource.add("description", dashboard.getDescription());

        resource.add("widgets", WidgetConfig.toResources(dashboard, dashboard.getWidgetConfigs()));

        resource.add(new Link("self", "http://localhost:8090/api/dashboards/" + dashboard.getId()));
        resource.add(new Link("widget-data", "http://localhost:8090/api/dashboards/" + dashboard.getId() + "/widget-data"));

        return resource;
    }

    public static List<HalJsonResource> toResources(List<Dashboard> dashboards) {
        return dashboards.stream()
                .map(Dashboard::toResource)
                .collect(Collectors.toList());
    }
}
