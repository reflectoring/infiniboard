package com.github.reflectoring.infiniboard.quartermaster.dashboard.domain;

import com.github.reflectoring.infiniboard.packrat.dashboard.Dashboard;
import com.github.reflectoring.infiniboard.packrat.dashboard.DashboardRepository;
import com.github.reflectoring.infiniboard.quartermaster.exception.ResourceAlreadyExistsException;
import com.github.reflectoring.infiniboard.quartermaster.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    try {
      return this.dashboardConfigRepository.save(dashboardConfig);
    } catch (DuplicateKeyException ex) {
      throw new ResourceAlreadyExistsException();
    }
  }

  public void delete(String slug) {

    Dashboard dashboard = load(slug);

    if (dashboard == null) {
      throw new ResourceNotFoundException();
    }

    this.dashboardConfigRepository.delete(dashboard.getId());
  }

  public Dashboard load(String slug) {
    Dashboard dashboard = this.dashboardConfigRepository.findOneBySlugOrId(slug, slug);

    if (dashboard == null) {
      throw new ResourceNotFoundException();
    }

    return dashboard;
  }

  public Page<Dashboard> loadAll(Pageable pageable) {
    return this.dashboardConfigRepository.findAll(pageable);
  }
}
