package com.github.reflectoring.infiniboard.quartermaster.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.reflectoring.haljson.HalJsonResource;
import com.github.reflectoring.haljson.Link;
import com.github.reflectoring.haljson.ResourceMapper;
import com.github.reflectoring.infiniboard.packrat.dashboard.DashboardConfig;
import com.github.reflectoring.infiniboard.quartermaster.widget.WidgetConfigMapper;

@Component
public class DashboardMapper implements ResourceMapper<DashboardConfig> {

    private WidgetConfigMapper widgetConfigMapper;

    @Autowired
    public DashboardMapper(WidgetConfigMapper widgetConfigMapper) {
        this.widgetConfigMapper = widgetConfigMapper;
    }

    @Override
    public HalJsonResource toResource(DashboardConfig source) {

        HalJsonResource resource = new HalJsonResource();
        resource.add("id", source.getId());
        resource.add("title", source.getTitle());
        resource.add("description", source.getDescription());

        resource.add("widgetConfigs", widgetConfigMapper.toResources(source.getWidgetConfigs()));

        resource.add(new Link("self", "http://localhost:8080/api/dashboards/" + source.getId()));

        return resource;
    }

}
