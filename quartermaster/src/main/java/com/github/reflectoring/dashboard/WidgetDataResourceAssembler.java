package com.github.reflectoring.dashboard;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WidgetDataResourceAssembler {


    WidgetDataResource toResource(Dashboard dashboard, WidgetData widgetData) {
        WidgetDataResource resource = new WidgetDataResource();

        resource.setWidgetId(widgetData.getWidgetId());
        resource.setDashboardId(widgetData.getDashboardId());
        resource.setData(widgetData.getData());

        return resource;

    }

    List<WidgetDataResource> toResources(Dashboard dashboard, List<WidgetData> widgetData) {
        return widgetData.stream()
                .map(wd -> toResource(dashboard, wd))
                .collect(Collectors.toList());
    }
}
