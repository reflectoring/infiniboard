package com.github.reflectoring.infiniboard.quartermaster.dashboard.domain;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.github.reflectoring.infiniboard.quartermaster.widget.domain.WidgetConfigService;

@Repository
public class DashboardRepository {

    private WidgetConfigService widgetConfigService;

    @Autowired
    public DashboardRepository(WidgetConfigService widgetConfigService) {
        this.widgetConfigService = widgetConfigService;
    }

    public Page<Dashboard> findAll(Pageable pageable) {
        ArrayList<Dashboard> dashboards = new ArrayList<>();
        dashboards.add(getSupportDashboardMock());
        return new PageImpl<>(dashboards);
    }

    private Dashboard getSupportDashboardMock() {
        Dashboard dashboard = new Dashboard(1, "Development", "Just what you need");

        dashboard.setWidgetConfigs(widgetConfigService.loadWidgets());

        return dashboard;
    }

    public Dashboard find(int id) {
        return getSupportDashboardMock();
    }

}
