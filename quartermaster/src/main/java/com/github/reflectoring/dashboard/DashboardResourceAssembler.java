package com.github.reflectoring.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class DashboardResourceAssembler extends ResourceAssemblerSupport<Dashboard, DashboardResource> {

    @Autowired
    private WidgetConfigResourceAssembler widgetAssembler;

    public DashboardResourceAssembler() {
        super(DashboardController.class, DashboardResource.class);
    }

    @Override
    public DashboardResource toResource(Dashboard dashboard) {
        DashboardResource resource = createResourceWithId(dashboard.getId(), dashboard);
        resource.setDashboardId(dashboard.getId());
        resource.setName(dashboard.getName());
        resource.setDescription(dashboard.getDescription());
        resource.setWidgetConfigs(widgetAssembler.toResources(dashboard, dashboard.getWidgetConfigs()));

        resource.add(linkTo(methodOn(DashboardController.class).getWidgetConfiguration(dashboard.getId())).withRel("widget-data"));

        return resource;
    }
}
