package com.github.reflectoring.repository;

import com.github.reflectoring.entity.WidgetEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepository extends MongoRepository<WidgetEntity, String> {
}
