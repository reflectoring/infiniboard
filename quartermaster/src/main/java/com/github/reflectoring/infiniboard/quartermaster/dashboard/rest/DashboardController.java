package com.github.reflectoring.infiniboard.quartermaster.dashboard.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.reflectoring.infiniboard.quartermaster.dashboard.domain.Dashboard;
import com.github.reflectoring.infiniboard.quartermaster.dashboard.domain.DashboardRepository;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/dashboards")
public class DashboardController {

    private DashboardRepository repository;

    private DashboardResourceAssembler dashboardResourceAssembler;

    @Autowired
    public DashboardController(DashboardRepository repository,
                               DashboardResourceAssembler dashboardResourceAssembler) {
        this.repository = repository;
        this.dashboardResourceAssembler = dashboardResourceAssembler;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResources<DashboardResource>> getAllDashboards(@PageableDefault Pageable pageable,
                                                                              PagedResourcesAssembler pagedResourcesAssembler) {
        Page<Dashboard> dashboardsPage = repository.findAll();
        PagedResources<DashboardResource> pagedResources =
                pagedResourcesAssembler.toResource(dashboardsPage, dashboardResourceAssembler);
        return new ResponseEntity<>(pagedResources, OK);
    }

    @RequestMapping(value = "/{id}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DashboardResource> getDashboard(@PathVariable int id) {
        Dashboard dashboard = repository.find(id);

        if (dashboard == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        DashboardResource resource = dashboardResourceAssembler.toResource(dashboard);
        return new ResponseEntity<>(resource, OK);
    }

}
