package com.github.reflectoring.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/api/dashboards")
@RestController
public class DashboardController {

    private DashboardRepository repository;

    private DashboardResourceAssembler assembler;
    private WidgetConfigResourceAssembler widgetConfigAssembler;
    private WidgetDataResourceAssembler widgetDataAssembler;

    @Autowired
    public DashboardController(DashboardRepository repository,
                               DashboardResourceAssembler assembler,
                               WidgetConfigResourceAssembler widgetConfigAssembler,
                               WidgetDataResourceAssembler widgetDataAssembler) {
        this.repository = repository;
        this.assembler = assembler;
        this.widgetConfigAssembler = widgetConfigAssembler;
        this.widgetDataAssembler = widgetDataAssembler;

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


    @RequestMapping(value = "/{id}/widget-data", method = GET)
    public ResponseEntity<List<WidgetDataResource>> getWidgetConfiguration(@PathVariable int id) {

        Dashboard dashboard = repository.find(id);
        List<WidgetData> data = repository.findWidgetData(id);
        List<WidgetDataResource> resources = widgetDataAssembler.toResources(dashboard, data);
        return new ResponseEntity<>(resources, OK);
    }

    @RequestMapping(value = "/{id}/widget-data/{widgetId}", method = GET)
    public ResponseEntity<WidgetDataResource> getWidgetConfiguration(@PathVariable int id, @PathVariable int widgetId) {


        Dashboard dashboard = repository.find(id);
        WidgetData data = repository.findWidgetData(id, widgetId);
        WidgetDataResource resources = widgetDataAssembler.toResource(dashboard, data);
        return new ResponseEntity<>(resources, OK);
    }

}
