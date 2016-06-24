package com.github.reflectoring.infiniboard.quartermaster.widget;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.reflectoring.haljson.HalJsonResource;
import com.github.reflectoring.haljson.Link;
import com.github.reflectoring.haljson.ResourceMapper;
import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;

@Component
public class WidgetConfigMapper implements ResourceMapper<WidgetConfig> {

    private SourceConfigMapper sourceConfigMapper;

    @Autowired
    public WidgetConfigMapper(SourceConfigMapper sourceConfigMapper) {
        this.sourceConfigMapper = sourceConfigMapper;
    }

    public HalJsonResource toResource(WidgetConfig widgetConfig) {
        HalJsonResource resource = new HalJsonResource();
        resource.add("id", widgetConfig.getId());
        resource.add("lastModified", widgetConfig.getLastModified());
        resource.add("title", widgetConfig.getTitle());

        List<SourceConfig>    sourceConfigs     = widgetConfig.getSourceConfigs();
        List<HalJsonResource> embeddedResources = sourceConfigMapper.toResources(sourceConfigs);
        resource.add("sourceConfigs", embeddedResources);

        resource.add(new Link("data", "http://localhost:8080/api/widgets/" + widgetConfig.getId() + "/data"));

        return resource;
    }

}
