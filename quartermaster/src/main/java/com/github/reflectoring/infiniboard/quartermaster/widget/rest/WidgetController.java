package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;
import com.github.reflectoring.infiniboard.quartermaster.widget.domain.WidgetConfigService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboards/{dashboardId}/widgets")
public class WidgetController {

  private WidgetConfigService widgetService;

  private WidgetConfigRepository widgetConfigRepository;

  @Autowired
  public WidgetController(
      WidgetConfigService widgetService, WidgetConfigRepository widgetConfigRepository) {
    this.widgetService = widgetService;
    this.widgetConfigRepository = widgetConfigRepository;
  }

  @RequestMapping(value = "/{widgetId}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<WidgetConfigResource> getWidget(
      @PathVariable String dashboardId, @PathVariable String widgetId) {
    WidgetConfig widgetConfig = widgetService.loadWidget(widgetId);

    if (widgetConfig == null) {
      return new ResponseEntity<>(NOT_FOUND);
    }

    WidgetConfigResourceAssembler assembler = new WidgetConfigResourceAssembler(dashboardId);
    WidgetConfigResource resource = assembler.toResource(widgetConfig);
    return new ResponseEntity<>(resource, OK);
  }

  @RequestMapping(
      value = "/{widgetId}",
      method = DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> deleteWidget(@PathVariable String widgetId) {
    boolean exists = widgetService.exists(widgetId);

    if (!exists) {
      return new ResponseEntity<>(NOT_FOUND);
    }

    widgetService.deleteWidget(widgetId);
    return new ResponseEntity<>(OK);
  }

  @RequestMapping(
      method = POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<WidgetConfigResource> createWidget(
      @PathVariable String dashboardId, @RequestBody WidgetConfigResource widgetConfigResource) {
    WidgetConfigResourceAssembler assembler = new WidgetConfigResourceAssembler(dashboardId);
    WidgetConfig createdWidgetConfig =
        widgetService.saveWidget(assembler.toEntity(widgetConfigResource));
    WidgetConfigResource resource = assembler.toResource(createdWidgetConfig);
    return new ResponseEntity<>(resource, OK);
  }

  @RequestMapping(
      value = "/{widgetId}/data",
      method = GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SourceDataResource> getData(
      @PathVariable String dashboardId, @PathVariable String widgetId) {
    SourceDataResourceAssembler assembler = new SourceDataResourceAssembler(dashboardId, widgetId);
    List<SourceData> data = widgetService.getData(widgetId);
    SourceDataResource resource = assembler.toResource(data);
    return new ResponseEntity<>(resource, OK);
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<WidgetConfigResource>> getWidgets(
      @PathVariable String dashboardId,
      @PageableDefault Pageable pageable,
      PagedResourcesAssembler pagedResourcesAssembler) {
    Page<WidgetConfig> widgetConfigPage = widgetConfigRepository.findAll(pageable);
    WidgetConfigResourceAssembler assembler = new WidgetConfigResourceAssembler(dashboardId);
    PagedResources<WidgetConfigResource> pagedResources =
        pagedResourcesAssembler.toResource(widgetConfigPage, assembler);
    return new ResponseEntity<>(pagedResources, OK);
  }

  @RequestMapping(value = "/all", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Resources<WidgetConfigResource> getAllWidgets(@PathVariable String dashboardId) {

    List<WidgetConfig> widgetConfigs = widgetConfigRepository.findAllByDashboardId(dashboardId);
    WidgetConfigResourceAssembler assembler = new WidgetConfigResourceAssembler(dashboardId);
    List<WidgetConfigResource> resources = assembler.toResources(widgetConfigs);

    Link self = linkTo(methodOn(WidgetController.class).getAllWidgets(dashboardId)).withRel("self");
    return new Resources<>(resources, self);
  }
}
