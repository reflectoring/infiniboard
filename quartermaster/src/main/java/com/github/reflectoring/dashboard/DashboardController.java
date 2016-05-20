package com.github.reflectoring.dashboard;

import com.github.reflectoring.HalJsonResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping("/api/dashboards")
@RestController
public class DashboardController {

    @RequestMapping(method = GET)
    public ResponseEntity<List<HalJsonResource>> getAllDashboardConfigurations() {
        List<Dashboard> dashboards = getDashboardMocks();
        List<HalJsonResource> resources = this.toResourceList(dashboards);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    private List<HalJsonResource> toResourceList(List<Dashboard> dashboards) {
        return dashboards.stream()
                .map(Dashboard::toResource)
                .collect(Collectors.toList());
    }

    private List<Dashboard> getDashboardMocks() {
        ArrayList<Dashboard> dashboards = new ArrayList<>();
        dashboards.add(new Dashboard(1, "Support", "Just what you need"));
        dashboards.add(new Dashboard(2, "Dev", "/dev/null"));
        return dashboards;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity<HalJsonResource> getDashboardConfiguration(@PathVariable int id) {
        Dashboard dashboard = getDashboardMock(id);
        HalJsonResource resources = dashboard.toResource();
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    private Dashboard getDashboardMock(int id) {
        return getDashboardMocks().stream()
                .filter(d -> d.getId() == id)
                .collect(Collectors.toList())
                .get(0);
    }
}
