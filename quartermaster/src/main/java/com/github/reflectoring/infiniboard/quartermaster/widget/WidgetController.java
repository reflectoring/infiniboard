package com.github.reflectoring.infiniboard.quartermaster.widget;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.reflectoring.haljson.HalJsonResource;
import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import com.github.reflectoring.infiniboard.packrat.source.SourceDataRepository;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/widgets")
public class WidgetController {

    private WidgetConfigRepository widgetConfigRepository;
    private WidgetConfigMapper     widgetConfigMapper;
    private SourceDataMapper       sourceDataMapper;
    private SourceDataRepository   sourceDataRepository;

    @Autowired
    public WidgetController(WidgetConfigRepository widgetConfigRepository,
                            SourceDataRepository sourceDataRepository,
                            WidgetConfigMapper widgetConfigMapper,
                            SourceDataMapper sourceDataMapper) {
        this.widgetConfigRepository = widgetConfigRepository;
        this.sourceDataRepository = sourceDataRepository;
        this.widgetConfigMapper = widgetConfigMapper;
        this.sourceDataMapper = sourceDataMapper;
    }

    @RequestMapping(value = "/{widgetId}", method = GET)
    public ResponseEntity<HalJsonResource> getOne(@PathVariable String widgetId) {
        WidgetConfig widgetConfig = widgetConfigRepository.findOne(widgetId);

        if (widgetConfig == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        HalJsonResource resource = widgetConfigMapper.toResource(widgetConfig);
        return new ResponseEntity<>(resource, OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<HalJsonResource> create(@RequestBody WidgetConfig widgetConfig) {
        widgetConfig.setLastModified(LocalDateTime.now());
        WidgetConfig    createdWidgetConfig = widgetConfigRepository.save(widgetConfig);
        HalJsonResource resource            = widgetConfigMapper.toResource(createdWidgetConfig);

        return new ResponseEntity<>(resource, OK);
    }

    @RequestMapping(value = "/{widgetId}/data", method = GET)
    public ResponseEntity<List<HalJsonResource>> getData(@PathVariable String widgetId) {
        List<SourceData>      data         = sourceDataRepository.findAllByWidgetId(widgetId);
        List<HalJsonResource> resourceList = sourceDataMapper.toResources(data);

        return new ResponseEntity<>(resourceList, OK);
    }

}
