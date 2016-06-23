package com.github.reflectoring.haljson;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A generic mapper interface to map any kind of Object to {@link HalJsonResource}. <p> <p>This interface provides a
 * default implementation for multiple Objects.</p>
 *
 * @param <T>
 *         Type of Object to Map
 */
public interface ResourceMapper<T> {

    HalJsonResource toResource(T source);

    default List<HalJsonResource> toResources(List<T> sources) {
        return sources
                .stream()
                .map(this::toResource)
                .collect(Collectors.toList());
    }
}
