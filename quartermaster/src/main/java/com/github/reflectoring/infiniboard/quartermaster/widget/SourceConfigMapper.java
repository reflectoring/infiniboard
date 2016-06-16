package com.github.reflectoring.infiniboard.quartermaster.widget;

import org.springframework.stereotype.Component;

import com.github.reflectoring.haljson.HalJsonResource;
import com.github.reflectoring.haljson.ResourceMapper;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;

@Component
public class SourceConfigMapper implements ResourceMapper<SourceConfig> {

    @Override
    public HalJsonResource toResource(SourceConfig source) {
        HalJsonResource resource = new HalJsonResource();
        resource.add("id", source.getId());
        resource.add("type", source.getType());
        resource.add("interval", source.getInterval());

        resource.add("configData", source.getConfigData());

        return resource;
    }

}
