package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.quartermaster.dashboard.rest.DashboardController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class WidgetConfigResourceAssembler extends ResourceAssemblerSupport<WidgetConfig, WidgetConfigResource> {

    private Integer dashboardId;

    public WidgetConfigResourceAssembler(Integer dashboardId) {
        super(WidgetController.class, WidgetConfigResource.class);
        this.dashboardId = dashboardId;
    }

    @Override
    public WidgetConfigResource toResource(WidgetConfig entity) {
        WidgetConfigResource resource = new WidgetConfigResource();
        resource.setTitle(entity.getTitle());
        resource.setType(entity.getType());
        resource.setUrl(entity.getUrl());
        resource.setDescription(entity.getDescription());
        resource.setLastModified(Date.from(entity.getLastModified().atZone(ZoneId.systemDefault()).toInstant()));

        for (SourceConfig config : entity.getSourceConfigs()) {
            resource.getSourceConfigs().add(config);
        }

        resource.add(linkTo(methodOn(WidgetController.class).getWidget(dashboardId, entity.getId())).withRel("self"));
        resource.add(linkTo(methodOn(DashboardController.class).getDashboard(dashboardId)).withRel("dashboard"));
        resource.add(linkTo(methodOn(WidgetController.class).getData(dashboardId, entity.getId())).withRel("data"));
        return resource;
    }

    public WidgetConfig toEntity(WidgetConfigResource resource) {
        WidgetConfig entity = new WidgetConfig();
        entity.setLastModified(LocalDateTime.now());
        entity.setTitle(resource.getTitle());
        entity.setType(resource.getType());
        entity.setSourceConfigs(resource.getSourceConfigs());
        entity.setUrl(resource.getUrl());
        entity.setDescription(resource.getDescription());
        return entity;
    }
}
