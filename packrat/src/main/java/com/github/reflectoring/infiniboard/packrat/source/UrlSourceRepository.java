package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * repository for UrlSource information
 */
public interface UrlSourceRepository extends MongoRepository<UrlSource, String> {

    long deleteById(long id);

    List<Integer> findAllIds();

}
