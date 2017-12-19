package com.github.reflectoring.infiniboard.quartermaster.dashboard.domain;

import com.github.reflectoring.infiniboard.packrat.dashboard.Dashboard;
import com.github.reflectoring.infiniboard.packrat.dashboard.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  private DashboardRepository dashboardConfigRepository;

  @Autowired
  public DashboardService(DashboardRepository dashboardConfigRepository) {
    this.dashboardConfigRepository = dashboardConfigRepository;
  }

  public Dashboard save(Dashboard dashboardConfig) {
    return this.dashboardConfigRepository.save(dashboardConfig);
  }

  public boolean exists(String dashboardId) {
    return this.dashboardConfigRepository.exists(dashboardId);
  }

  public void delete(String dashboardId) {
    this.dashboardConfigRepository.delete(dashboardId);
  }

  public Dashboard load(String dashboardId) {
    return this.dashboardConfigRepository.findOne(dashboardId);
  }

  public Page<Dashboard> loadAll(Pageable pageable) {
    return this.dashboardConfigRepository.findAll(pageable);
  }
}
