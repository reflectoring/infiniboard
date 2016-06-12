package com.github.reflectoring.infiniboard.packrat.dashboard;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * repository for dashboard configuration
 */
public interface DashboardConfigRepository extends MongoRepository<DashboardConfig, String> {
}
