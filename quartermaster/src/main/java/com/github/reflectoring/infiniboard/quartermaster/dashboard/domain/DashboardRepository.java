package com.github.reflectoring.infiniboard.quartermaster.dashboard.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.reflectoring.infiniboard.quartermaster.widget.domain.WidgetConfigService;

@Repository
public class DashboardRepository {

    private WidgetConfigService widgetConfigService;

    @Autowired
    public DashboardRepository(WidgetConfigService widgetConfigService) {
        this.widgetConfigService = widgetConfigService;
    }

    public List<Dashboard> findAll() {
        ArrayList<Dashboard> dashboards = new ArrayList<>();
        dashboards.add(getSupportDashboardMock());
        return dashboards;
    }

    private Dashboard getSupportDashboardMock() {
        Dashboard dashboard = new Dashboard(1, "Development", "Just what you need");

        dashboard.setWidgetConfigs(widgetConfigService.loadWidgets());

        return dashboard;
    }

    public Dashboard find(int id) {
        return findAll().stream()
                .filter(d -> d.getId() == id)
                .collect(Collectors.toList())
                .get(0);
    }

}
