package com.github.reflectoring.infiniboard.packrat.widget;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WidgetConfigRepository extends MongoRepository<WidgetConfig, String> {

    WidgetConfig findByTitle(String title);

    List<WidgetConfig> findAllByLastModifiedAfter(LocalDateTime date);

    Page<WidgetConfig> findAll(Pageable pageable);

}
