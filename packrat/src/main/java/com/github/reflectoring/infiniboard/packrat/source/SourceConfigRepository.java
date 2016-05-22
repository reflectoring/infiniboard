package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * repository for sources
 */
public interface SourceConfigRepository extends MongoRepository<SourceConfig, String> {

    SourceConfig findById(String id);

    List<SourceConfig> findByWidgetId(String widgetId);

    List<SourceConfig> findByLastModifiedAfter(Date date);

    List<SourceConfig> findByDeleted(boolean isDeleted);
}
