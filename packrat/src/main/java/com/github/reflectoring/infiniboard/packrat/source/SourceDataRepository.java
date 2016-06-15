package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SourceDataRepository extends MongoRepository<SourceData, String> {

    List<SourceData> findAllByWidgetId(String widgetId);

    SourceData findByWidgetIdAndSourceId(String widgetId, String sourceId);

    void deleteByWidgetId(String widgetId);

}
