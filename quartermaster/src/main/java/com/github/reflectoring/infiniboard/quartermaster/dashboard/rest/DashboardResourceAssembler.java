package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.reflectoring.infiniboard.quartermaster.dashboard.domain.Dashboard;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class DashboardResourceAssembler extends ResourceAssemblerSupport<Dashboard, DashboardResource> {

    public DashboardResourceAssembler() {
        super(DashboardController.class, DashboardResource.class);
    }

    @Override
    public DashboardResource toResource(Dashboard entity) {
        DashboardResource resource = new DashboardResource();
        resource.setName(entity.getName());
        resource.setDescription(entity.getDescription());
        resource.add(
                linkTo(methodOn(DashboardController.class).getDashboard(entity.getId())).withRel("self"));
        // TODO: add link to WidgetConfigs of the dashboard once REST API for this operation is available
        return resource;
    }
}
