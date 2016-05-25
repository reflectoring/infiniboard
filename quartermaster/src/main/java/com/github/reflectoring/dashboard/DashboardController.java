package com.github.reflectoring.dashboard;

import com.github.reflectoring.haljson.HalJsonResource;
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

    @Autowired
    public DashboardController(DashboardRepository repository) {
        this.repository = repository;

    }

    @RequestMapping(method = GET)
    public ResponseEntity<List<HalJsonResource>> getAllDashboardConfigurations() {
        List<Dashboard> dashboards = repository.findAll();
        List<HalJsonResource> resources = Dashboard.toResources(dashboards);
        return new ResponseEntity<>(resources, OK);
    }


    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity<HalJsonResource> getDashboardConfiguration(@PathVariable int id) {
        Dashboard dashboard = repository.find(id);
        HalJsonResource resources = Dashboard.toResource(dashboard);
        return new ResponseEntity<>(resources, OK);
    }


    @RequestMapping(value = "/{id}/widget-data", method = GET)
    public ResponseEntity<List<HalJsonResource>> getWidgetConfiguration(@PathVariable int id) {

        Dashboard dashboard = repository.find(id);
        List<WidgetData> widgetConfigs = repository.findWidgetData(id);
        List<HalJsonResource> resources = WidgetData.toResources(dashboard, widgetConfigs);
        return new ResponseEntity<>(resources, OK);
    }

    @RequestMapping(value = "/{id}/widget-data/{widgetId}", method = GET)
    public ResponseEntity<HalJsonResource> getWidgetConfiguration(@PathVariable int id, @PathVariable int widgetId) {

        Dashboard dashboard = repository.find(id);
        WidgetData widgetConfig = repository.findWidgetData(id, widgetId);
        HalJsonResource resources = WidgetData.toResource(dashboard, widgetConfig);
        return new ResponseEntity<>(resources, OK);
    }

}
