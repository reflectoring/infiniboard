package com.github.reflectoring.infiniboard.packrat.source;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * repository for Source information
 */
public interface SourceRepository extends MongoRepository<UrlSource, String> {

    long deleteById(long id);

    List<Integer> findAllIds();
}
