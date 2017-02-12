package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.quartermaster.dashboard.rest.DashboardController;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class WidgetConfigResourceAssembler
    extends ResourceAssemblerSupport<WidgetConfig, WidgetConfigResource> {

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
    resource.setTitleUrl(entity.getTitleUrl());
    resource.setDescription(entity.getDescription());
    resource.setLastModified(
        Date.from(entity.getLastModified().atZone(ZoneId.systemDefault()).toInstant()));
    for (SourceConfig config : entity.getSourceConfigs()) {
      resource.getSourceConfigs().add(removeCredentials(config));
    }
    resource.add(
        linkTo(methodOn(WidgetController.class).getWidget(dashboardId, entity.getId()))
            .withRel("self"));
    resource.add(
        linkTo(methodOn(DashboardController.class).getDashboard(dashboardId)).withRel("dashboard"));
    resource.add(
        linkTo(methodOn(WidgetController.class).getData(dashboardId, entity.getId()))
            .withRel("data"));
    return resource;
  }

  private SourceConfig removeCredentials(SourceConfig config) {
    Map<String, Object> configData = config.getConfigData();
    if (configData.containsKey("password")) {
      configData.put("password", "**********");
    }

    if (configData.containsKey("username")) {
      configData.put("username", "**********");
    }

    return config;
  }

  public WidgetConfig toEntity(WidgetConfigResource resource) {
    WidgetConfig entity = new WidgetConfig();
    entity.setLastModified(LocalDateTime.now());
    entity.setTitle(resource.getTitle());
    entity.setType(resource.getType());
    entity.setSourceConfigs(resource.getSourceConfigs());
    entity.setTitleUrl(resource.getTitleUrl());
    entity.setDescription(resource.getDescription());
    return entity;
  }
}
