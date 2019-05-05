package com.github.reflectoring.infiniboard.packrat.dashboard;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DashboardRepository extends MongoRepository<Dashboard, String> {

  Dashboard findOneBySlugOrId(String slug, String id);
}
