package com.github.reflectoring.infiniboard.packrat.source;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SourceDataRepository extends MongoRepository<SourceData, String> {

    List<SourceData> findAllByWidgetId(String widgetId);

    SourceData findByWidgetIdAndSourceId(String widgetId, String sourceId);

}
