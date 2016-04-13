package com.github.reflectoring.infiniboard.harvester.source.url;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * repository for UrlSource information
 */
public interface UrlSourceRepository extends MongoRepository<UrlSource, String> {

}
