package com.github.reflectoring.dashboard;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DashboardResourceAssembler extends ResourceAssemblerSupport<Dashboard, DashboardResource> {


    public DashboardResourceAssembler() {
        super(DashboardController.class, DashboardResource.class);
    }

    @Override
    public DashboardResource toResource(Dashboard dashboard) {
        DashboardResource resource = createResourceWithId(dashboard.getId(), dashboard);
        resource.setName(dashboard.getName());
        resource.setDescription(dashboard.getDescription());

        return resource;
    }

    List<DashboardResource> toResourceList(List<Dashboard> dashboards) {
        return dashboards.stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }
}
