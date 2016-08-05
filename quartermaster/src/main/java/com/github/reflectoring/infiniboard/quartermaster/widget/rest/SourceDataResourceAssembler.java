package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.github.reflectoring.infiniboard.packrat.source.SourceData;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class SourceDataResourceAssembler extends ResourceAssemblerSupport<List<SourceData>, SourceDataResource> {

    private Integer dashboardId;
    private String  widgetId;

    public SourceDataResourceAssembler(Integer dashboardId, String widgetId) {
        super(WidgetController.class, SourceDataResource.class);
        this.dashboardId = dashboardId;
        this.widgetId = widgetId;
    }

    @Override
    public SourceDataResource toResource(List<SourceData> entity) {
        SourceDataResource resource = new SourceDataResource();
        resource.getSourceData().addAll(entity);
        resource.add(linkTo(methodOn(WidgetController.class).getWidget(dashboardId, widgetId)).withRel("widget"));
        return resource;
    }
}
