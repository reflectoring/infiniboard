package com.github.reflectoring.infiniboard.quartermaster.dashboard;

import com.github.reflectoring.haljson.Link;
import com.github.reflectoring.haljson.HalJsonResource;

import java.util.List;
import java.util.stream.Collectors;

public class WidgetConfig {

    private int id;
    private String type;
    private int interval;

    public WidgetConfig(int id, String type, int interval) {
        this.id = id;
        this.type = type;
        this.interval = interval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public static HalJsonResource toResource(Dashboard dashboard, WidgetConfig widgetConfig) {
        HalJsonResource resource = new HalJsonResource();
        resource.add("id", widgetConfig.getId());
        resource.add("type", widgetConfig.getType());
        resource.add("interval", widgetConfig.getInterval());

        resource.add(new Link("data", "http://localhost:8090/api/dashboards/" + dashboard.getId() + "/widget-data/" + widgetConfig.getId()));

        return resource;
    }

    public static List<HalJsonResource> toResources(Dashboard dashboard, List<WidgetConfig> widgetConfigs) {
        return widgetConfigs.stream()
                .map(config -> WidgetConfig.toResource(dashboard, config))
                .collect(Collectors.toList());
    }
}
