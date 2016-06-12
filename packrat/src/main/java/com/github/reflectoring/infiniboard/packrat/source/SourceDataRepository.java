package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * repository for data retrieved by sources
 */
public interface SourceDataRepository extends MongoRepository<SourceData, String> {

    List<SourceData> findAllByWidgetId(String widgetId);

    SourceData findByWidgetIdAndSourceId(String widgetId, String sourceId);

}
