package com.github.reflectoring.dashboard;

import com.github.reflectoring.haljson.Json;
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

    public List<WidgetData> findWidgetData(int dashboardId) {
        List<WidgetData> data = new ArrayList<>();

        Json data1 = new Json();
        data1.add("name", "harvester");
        data1.add("duration", "1:23 min");
        data1.add("status", "Failure");
        data1.add("url", "http://localhost/jenkins/harvester");
        data1.add("buildUrl", "http://localhost/jenkins/harvester/123");

        data.add(new WidgetData(1, 1, data1));

        Json data2 = new Json();
        data2.add("name", "quartermaster");
        data2.add("duration", "4:15 min");
        data2.add("status", "Unstable");
        data2.add("url", "http://localhost/jenkins/quartermaster");
        data2.add("buildUrl", "http://localhost/jenkins/quartermaster/123");

        data.add(new WidgetData(1, 2, data2));

        Json data3 = new Json();
        data3.add("name", "infiniboard");
        data3.add("duration", "2:21 min");
        data3.add("status", "Success");
        data3.add("url", "http://localhost/jenkins/infiniboard");
        data3.add("buildUrl", "http://localhost/jenkins/infiniboard/123");

        data.add(new WidgetData(1, 3, data3));

        Json data4 = new Json();
        data4.add("name", "Development");
        data4.add("version", "0.1.0");
        data4.add("status", "ok");

        data.add(new WidgetData(2, 1, data4));

        return data.stream()
                .filter(wd -> wd.getDashboardId() == dashboardId)
                .collect(Collectors.toList());
    }

    public WidgetData findWidgetData(int dashboardId, int widgetId) {

        return this.findWidgetData(dashboardId).stream()
                .filter(wd -> wd.getWidgetId() == widgetId)
                .collect(Collectors.toList())
                .get(0);
    }

}
