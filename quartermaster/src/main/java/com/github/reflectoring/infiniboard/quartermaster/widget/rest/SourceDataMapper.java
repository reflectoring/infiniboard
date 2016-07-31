package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import org.springframework.stereotype.Component;

import com.github.reflectoring.haljson.HalJsonResource;
import com.github.reflectoring.haljson.ResourceMapper;
import com.github.reflectoring.infiniboard.packrat.source.SourceData;

@Component
public class SourceDataMapper implements ResourceMapper<SourceData> {

    public HalJsonResource toResource(SourceData sourceData) {
        HalJsonResource resource = new HalJsonResource();
        resource.add("id", sourceData.getId());
        resource.add("sourceId", sourceData.getSourceId());
        resource.add("widgetId", sourceData.getWidgetId());
        resource.add("data", sourceData.getData());

        return resource;
    }
}

