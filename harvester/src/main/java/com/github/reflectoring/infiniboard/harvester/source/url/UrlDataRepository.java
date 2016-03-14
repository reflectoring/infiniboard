package com.github.reflectoring.infiniboard.harvester.source.url;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * repository for UrlData information
 */
public interface UrlDataRepository extends MongoRepository<UrlData, String> {

}
