package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import java.util.List;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class SourceDataResourceAssembler
    extends ResourceAssemblerSupport<List<SourceData>, SourceDataResource> {

  private String dashboardId;
  private String widgetId;

  public SourceDataResourceAssembler(String dashboardId, String widgetId) {
    super(WidgetController.class, SourceDataResource.class);
    this.dashboardId = dashboardId;
    this.widgetId = widgetId;
  }

  @Override
  public SourceDataResource toResource(List<SourceData> entity) {
    SourceDataResource resource = new SourceDataResource();
    resource.getSourceData().addAll(entity);
    resource.add(
        linkTo(methodOn(WidgetController.class).getWidget(dashboardId, widgetId))
            .withRel("widget"));
    return resource;
  }
}
