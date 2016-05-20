package com.github.reflectoring.dashboard;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DashboardRepository {

    public List<Dashboard> findAll() {
        ArrayList<Dashboard> dashboards = new ArrayList<>();
        dashboards.add(getSupportDashboardMock());
        dashboards.add(getDevDashboard());
        return dashboards;
    }

    private Dashboard getDevDashboard() {
        Dashboard dashboard = new Dashboard(2, "Dev", "/dev/null");
        List<WidgetConfig> widgetConfigs = dashboard.getWidgetConfigs();

        WidgetConfig jobWidget1 = new WidgetConfig(1, "platform-status", 20_000);
        widgetConfigs.add(jobWidget1);

        return dashboard;
    }

    private Dashboard getSupportDashboardMock() {
        Dashboard dashboard = new Dashboard(1, "Support", "Just what you need");

        List<WidgetConfig> widgetConfigs = dashboard.getWidgetConfigs();
        WidgetConfig jobWidget1 = new WidgetConfig(1, "jenkins-job", 3_000);
        WidgetConfig jobWidget2 = new WidgetConfig(2, "jenkins-job", 5_000);
        WidgetConfig jobWidget3 = new WidgetConfig(3, "jenkins-job", 10_000);

        widgetConfigs.add(jobWidget1);
        widgetConfigs.add(jobWidget2);
        widgetConfigs.add(jobWidget3);

        return dashboard;
    }


    public Dashboard find(int id) {
        return findAll().stream()
                .filter(d -> d.getId() == id)
                .collect(Collectors.toList())
                .get(0);
    }

}
