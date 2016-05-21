package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * repository for Result information
 */
public interface ResultRepository extends MongoRepository<Result, String> {

}
