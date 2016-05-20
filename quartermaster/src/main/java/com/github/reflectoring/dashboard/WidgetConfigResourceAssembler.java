package com.github.reflectoring.dashboard;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class WidgetConfigResourceAssembler {


    public WidgetConfigResource toResource(Dashboard dashboard, WidgetConfig widgetConfig) {
        WidgetConfigResource resource = new WidgetConfigResource();

        resource.add(linkTo(methodOn(DashboardController.class).getWidgetConfiguration(dashboard.getId())).slash(widgetConfig.getId()).withSelfRel());

        resource.setWidgetId(widgetConfig.getId());
        resource.setType(widgetConfig.getType());
        resource.setInterval(widgetConfig.getInterval());

        return resource;
    }

    public List<WidgetConfigResource> toResources(Dashboard dashboard, List<WidgetConfig> widgetConfig) {
        return widgetConfig.stream()
                .map(wc -> toResource(dashboard, wc))
                .collect(Collectors.toList());
    }
}
