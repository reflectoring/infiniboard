package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * repository for sources
 */
public interface SourceConfigRepository extends MongoRepository<SourceConfig, String> {

    List<SourceConfig> findByPluginId(String pluginId);

    List<SourceConfig> findByWidgetId(String widgetId);

}
