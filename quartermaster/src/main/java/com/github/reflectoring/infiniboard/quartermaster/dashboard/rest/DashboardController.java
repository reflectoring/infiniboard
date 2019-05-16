package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.github.reflectoring.infiniboard.packrat.dashboard.Dashboard;
import com.github.reflectoring.infiniboard.quartermaster.dashboard.domain.DashboardService;
import com.github.reflectoring.infiniboard.quartermaster.exception.ErrorResource;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboards")
public class DashboardController {

  private DashboardService service;

  private DashboardResourceAssembler dashboardResourceAssembler;

  @Autowired
  public DashboardController(
      DashboardService service, DashboardResourceAssembler dashboardResourceAssembler) {
    this.service = service;
    this.dashboardResourceAssembler = dashboardResourceAssembler;
  }

  @RequestMapping(
      method = POST,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResourceSupport> create(
      @RequestBody @Valid DashboardResource dashboardResource, Errors result) {

    if (result.hasErrors()) {
      return new ResponseEntity<>(new ErrorResource(result), BAD_REQUEST);
    }

    Dashboard savedDashboard = service.save(dashboardResourceAssembler.toEntity(dashboardResource));
    DashboardResource resource = dashboardResourceAssembler.toResource(savedDashboard);
    return new ResponseEntity<>(resource, OK);
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<DashboardResource>> getAllDashboards(
      @PageableDefault Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler) {

    Page<Dashboard> dashboardsPage = service.loadAll(pageable);
    PagedResources<DashboardResource> pagedResources =
        pagedResourcesAssembler.toResource(dashboardsPage, dashboardResourceAssembler);
    return new ResponseEntity<>(pagedResources, OK);
  }

  @RequestMapping(value = "/{slug}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DashboardResource> getDashboard(@PathVariable String slug) {
    Dashboard dashboard = service.load(slug);

    DashboardResource resource = dashboardResourceAssembler.toResource(dashboard);
    return new ResponseEntity<>(resource, OK);
  }
}
