package com.github.reflectoring.infiniboard.quartermaster.testframework.factory;

import com.github.reflectoring.infiniboard.packrat.dashboard.Dashboard;
import java.util.ArrayList;
import java.util.List;

public class DashboardFactory {

  public static List<Dashboard> dashboardList() {
    List<Dashboard> list = new ArrayList<>();
    list.add(dashboard());
    return list;
  }

  public static Dashboard dashboard() {
    Dashboard dashboard = new Dashboard("Test Dashboard");
    dashboard.setId("test-dashboard-id");
    dashboard.setDescription("Testing Dashboard");
    return dashboard;
  }
}
