package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.reflectoring.haljson.HalJsonResource;
import com.github.reflectoring.haljson.Link;
import com.github.reflectoring.haljson.ResourceMapper;
import com.github.reflectoring.infiniboard.quartermaster.dashboard.domain.Dashboard;
import com.github.reflectoring.infiniboard.quartermaster.widget.rest.WidgetConfigMapper;

@Component
public class DashboardMapper implements ResourceMapper<Dashboard> {

    private WidgetConfigMapper widgetConfigMapper;

    @Autowired
    public DashboardMapper(WidgetConfigMapper widgetConfigMapper) {
        this.widgetConfigMapper = widgetConfigMapper;
    }

    @Override
    public HalJsonResource toResource(Dashboard source) {

        HalJsonResource resource = new HalJsonResource();
        resource.add("id", source.getId());
        resource.add("name", source.getName());
        resource.add("description", source.getDescription());

        resource.add("widgets", widgetConfigMapper.toResources(source.getWidgetConfigs()));

        resource.add(new Link("self", "http://localhost:8080/api/dashboards/" + source.getId()));

        return resource;
    }

}
