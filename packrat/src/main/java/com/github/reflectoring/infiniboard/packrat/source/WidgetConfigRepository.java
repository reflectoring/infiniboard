package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * repository for widget configurations
 */
public interface WidgetConfigRepository extends MongoRepository<WidgetConfig, String> {

    WidgetConfig findByTitle(String title);

    List<WidgetConfig> findByLastModifiedAfter(LocalDate date);

}
