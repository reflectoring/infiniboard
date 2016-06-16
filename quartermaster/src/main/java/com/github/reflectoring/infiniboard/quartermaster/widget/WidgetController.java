package com.github.reflectoring.infiniboard.quartermaster.widget;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.reflectoring.haljson.HalJsonResource;
import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/widgets")
public class WidgetController {

    private WidgetConfigService widgetService;
    private WidgetConfigMapper  widgetConfigMapper;
    private SourceDataMapper    sourceDataMapper;

    @Autowired
    public WidgetController(WidgetConfigService widgetService,
                            WidgetConfigMapper widgetConfigMapper,
                            SourceDataMapper sourceDataMapper) {

        this.widgetService = widgetService;
        this.widgetConfigMapper = widgetConfigMapper;
        this.sourceDataMapper = sourceDataMapper;
    }

    @RequestMapping(value = "/{widgetId}", method = GET)
    public ResponseEntity<HalJsonResource> getWidget(@PathVariable String widgetId) {
        WidgetConfig    widgetConfig = widgetService.loadWidget(widgetId);
        HalJsonResource resource     = widgetConfigMapper.toResource(widgetConfig);

        return new ResponseEntity<>(resource, OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<HalJsonResource> getWidget(@RequestBody WidgetConfig widgetConfig) {
        WidgetConfig    createdWidgetConfig = widgetService.saveWidget(widgetConfig);
        HalJsonResource resource            = widgetConfigMapper.toResource(createdWidgetConfig);

        return new ResponseEntity<>(resource, OK);
    }

    @RequestMapping(value = "/{widgetId}/data", method = GET)
    public ResponseEntity<List<HalJsonResource>> getData(@PathVariable String widgetId) {
        List<SourceData> data = widgetService.getData(widgetId);

        List<HalJsonResource> resourceList = sourceDataMapper.toResources(data);
        return new ResponseEntity<>(resourceList, OK);
    }

}
