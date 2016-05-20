package com.github.reflectoring.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/api/dashboards")
@RestController
public class DashboardController {

    private DashboardRepository repository;

    private DashboardResourceAssembler assembler;
    private WidgetConfigResourceAssembler widgetAssembler;

    @Autowired
    public DashboardController(DashboardRepository repository,
                               DashboardResourceAssembler assembler,
                               WidgetConfigResourceAssembler widgetAssembler) {
        this.repository = repository;
        this.assembler = assembler;
        this.widgetAssembler = widgetAssembler;

    }

    @RequestMapping(method = GET)
    public ResponseEntity<List<DashboardResource>> getAllDashboardConfigurations() {
        List<Dashboard> dashboards = repository.findAll();
        List<DashboardResource> resources = assembler.toResources(dashboards);
        return new ResponseEntity<>(resources, OK);
    }


    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity<DashboardResource> getDashboardConfiguration(@PathVariable int id) {
        Dashboard dashboard = repository.find(id);
        DashboardResource resources = assembler.toResource(dashboard);
        return new ResponseEntity<>(resources, OK);
    }


    @RequestMapping(value = "/{id}/widgets", method = GET)
    public ResponseEntity<List<WidgetConfigResource>> getWidgetConfiguration(@PathVariable int id) {

        Dashboard dashboard = repository.find(id);
        List<WidgetConfig> widgetConfigs = dashboard.getWidgetConfigs();
        List<WidgetConfigResource> resources = widgetAssembler.toResources(dashboard, widgetConfigs);
        return new ResponseEntity<>(resources, OK);
    }

    @RequestMapping(value = "/{id}/widgets/{widgetId}", method = GET)
    public ResponseEntity<WidgetConfigResource> getWidgetConfiguration(@PathVariable int id, @PathVariable int widgetId) {

        Dashboard dashboard = repository.find(id);
        List<WidgetConfig> widgetConfigs = dashboard.getWidgetConfigs();


        WidgetConfig widgetConfig = widgetConfigs.stream()
                .filter(wc -> wc.getId() == widgetId)
                .collect(Collectors.toList())
                .get(0);

        WidgetConfigResource resources = widgetAssembler.toResource(dashboard, widgetConfig);
        return new ResponseEntity<>(resources, OK);
    }

}
