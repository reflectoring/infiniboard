package com.github.reflectoring.repository;

import com.github.reflectoring.entity.WidgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WidgetRepository extends JpaRepository<WidgetEntity, Long> {
}
