package com.github.reflectoring.infiniboard.quartermaster.widget;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;

@RequestMapping("/api/widgets")
@RestController
public class WidgetController {

    private WidgetConfigService widgetService;

    @Autowired
    public WidgetController(WidgetConfigService widgetService) {
        this.widgetService = widgetService;
    }

    @RequestMapping(value = "/{widgetId}", method = RequestMethod.GET)
    public WidgetConfig getWidget(@PathVariable String widgetId) {
        return widgetService.loadWidget(widgetId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public WidgetConfig getWidget(@RequestBody WidgetConfig widgetConfig) {
        return widgetService.saveWidget(widgetConfig);
    }

    @RequestMapping(value = "/{widgetId}/data", method = RequestMethod.GET)
    public List<SourceData> getData(@PathVariable String widgetId) {
        return widgetService.getData(widgetId);
    }

}
