package com.github.reflectoring.infiniboard.quartermaster.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.reflectoring.haljson.HalJsonResource;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/dashboards")
public class DashboardController {

    private DashboardRepository repository;
    private DashboardMapper dashboardMapper;

    @Autowired
    public DashboardController(DashboardRepository repository, DashboardMapper dashboardMapper) {
        this.repository = repository;
        this.dashboardMapper = dashboardMapper;
    }

    @RequestMapping(method = GET)
    public ResponseEntity<List<HalJsonResource>> getAllDashboardConfigurations() {
        List<Dashboard> dashboards = repository.findAll();
        List<HalJsonResource> resources = dashboardMapper.toResources(dashboards);

        return new ResponseEntity<>(resources, OK);
    }


    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity<HalJsonResource> getDashboardConfiguration(@PathVariable int id) {
        Dashboard dashboard = repository.find(id);

        if (dashboard == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        HalJsonResource resources = dashboardMapper.toResource(dashboard);
        return new ResponseEntity<>(resources, OK);
    }

}
