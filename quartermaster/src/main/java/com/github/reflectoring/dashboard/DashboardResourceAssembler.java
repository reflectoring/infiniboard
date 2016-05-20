package com.github.reflectoring.dashboard;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Component
public class DashboardResourceAssembler extends ResourceAssemblerSupport<Dashboard, DashboardResource> {


    public DashboardResourceAssembler() {
        super(DashboardController.class, DashboardResource.class);
    }

    @Override
    public DashboardResource toResource(Dashboard dashboard) {
        DashboardResource resource = createResourceWithId(dashboard.getId(), dashboard);
        resource.setDashboardId(dashboard.getId());
        resource.setName(dashboard.getName());
        resource.setDescription(dashboard.getDescription());

        resource.add(linkTo(methodOn(DashboardController.class).getWidgetConfiguration(dashboard.getId())).withRel("widgets"));

        return resource;
    }
}
