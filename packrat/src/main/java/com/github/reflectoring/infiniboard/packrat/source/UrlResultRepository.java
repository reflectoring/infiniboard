package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * repository for UrlResult information
 */
public interface UrlResultRepository extends MongoRepository<UrlResult, String> {

    UrlResult findByUrlSource(UrlSource urlSource);
}
