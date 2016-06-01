package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * repository for widget information
 */
public interface WidgetRepository extends MongoRepository<Widget, String> {

}
