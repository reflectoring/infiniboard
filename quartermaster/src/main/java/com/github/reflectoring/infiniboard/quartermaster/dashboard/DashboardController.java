package com.github.reflectoring.infiniboard.quartermaster.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.reflectoring.haljson.HalJsonResource;
import com.github.reflectoring.infiniboard.packrat.dashboard.DashboardConfig;
import com.github.reflectoring.infiniboard.packrat.dashboard.DashboardConfigRepository;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/dashboards")
public class DashboardController {

    private DashboardConfigRepository dashboardConfigRepository;
    private WidgetConfigRepository    widgetConfigRepository;
    private DashboardMapper           dashboardMapper;

    @Autowired
    public DashboardController(DashboardConfigRepository dashboardConfigRepository,
                               WidgetConfigRepository widgetConfigRepository,
                               DashboardMapper dashboardMapper) {
        this.dashboardConfigRepository = dashboardConfigRepository;
        this.widgetConfigRepository = widgetConfigRepository;
        this.dashboardMapper = dashboardMapper;
    }

    @RequestMapping(method = GET)
    public ResponseEntity<List<HalJsonResource>> getAll() {
        List<DashboardConfig> dashboards = dashboardConfigRepository.findAll();

        dashboards.stream().map(this::addAllWidgetsToDashboard);
        List<HalJsonResource> resources  = dashboardMapper.toResources(dashboards);

        return new ResponseEntity<>(resources, OK);
    }


    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity<HalJsonResource> getOne(@PathVariable String id) {
        DashboardConfig dashboard = dashboardConfigRepository.findOne(id);

        addAllWidgetsToDashboard(dashboard);


        if (dashboard == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        HalJsonResource resources = dashboardMapper.toResource(dashboard);
        return new ResponseEntity<>(resources, OK);
    }

    private DashboardConfig addAllWidgetsToDashboard(DashboardConfig dashboard) {
        // Workaround until Dashboard is assosiated with widgets
        dashboard.setWidgetConfigs(widgetConfigRepository.findAll());
        return dashboard;
    }


    @RequestMapping(method = POST)
    public ResponseEntity<HalJsonResource> create(@RequestBody DashboardConfig dashboardConfig) {
        DashboardConfig saved    = dashboardConfigRepository.save(dashboardConfig);
        HalJsonResource resource = dashboardMapper.toResource(saved);

        return new ResponseEntity<>(resource, OK);
    }

}
