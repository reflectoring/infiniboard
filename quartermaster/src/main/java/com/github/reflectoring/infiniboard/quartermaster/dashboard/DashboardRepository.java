package com.github.reflectoring.infiniboard.quartermaster.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.github.reflectoring.haljson.HalJsonResource;
import com.github.reflectoring.infiniboard.quartermaster.widget.WidgetConfigService;

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

    public List<WidgetData> findWidgetData(int dashboardId) {
        List<WidgetData> data = new ArrayList<>();

        HalJsonResource data1 = new HalJsonResource();
        data1.add("name", "harvester");
        data1.add("duration", "1:23 min");
        data1.add("status", "Failure");
        data1.add("url", "http://localhost/jenkins/harvester");
        data1.add("buildUrl", "http://localhost/jenkins/harvester/123");

        data.add(new WidgetData(1, 1, data1));

        HalJsonResource data2 = new HalJsonResource();
        data2.add("name", "quartermaster");
        data2.add("duration", "4:15 min");
        data2.add("status", "Unstable");
        data2.add("url", "http://localhost/jenkins/quartermaster");
        data2.add("buildUrl", "http://localhost/jenkins/quartermaster/123");

        data.add(new WidgetData(1, 2, data2));

        HalJsonResource data3 = new HalJsonResource();
        data3.add("name", "infiniboard");
        data3.add("duration", "2:21 min");
        data3.add("status", "Success");
        data3.add("url", "http://localhost/jenkins/infiniboard");
        data3.add("buildUrl", "http://localhost/jenkins/infiniboard/123");

        data.add(new WidgetData(1, 3, data3));

        HalJsonResource data4 = new HalJsonResource();
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
