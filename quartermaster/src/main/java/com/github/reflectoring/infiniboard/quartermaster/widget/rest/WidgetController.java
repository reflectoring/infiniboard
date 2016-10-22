package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;
import com.github.reflectoring.infiniboard.quartermaster.widget.domain.WidgetConfigService;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/dashboards/{dashboardId}/widgets")
public class WidgetController {

    private WidgetConfigService widgetService;

    private WidgetConfigRepository widgetConfigRepository;

    @Autowired
    public WidgetController(WidgetConfigService widgetService, WidgetConfigRepository widgetConfigRepository) {
        this.widgetService = widgetService;
        this.widgetConfigRepository = widgetConfigRepository;
    }

    @RequestMapping(value = "/{widgetId}", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WidgetConfigResource> getWidget(@PathVariable Integer dashboardId,
                                                          @PathVariable String widgetId) {
        WidgetConfig widgetConfig = widgetService.loadWidget(widgetId);

        if (widgetConfig == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        WidgetConfigResourceAssembler assembler = new WidgetConfigResourceAssembler(dashboardId);
        WidgetConfigResource          resource  = assembler.toResource(widgetConfig);
        return new ResponseEntity<>(resource, OK);
    }

    @RequestMapping(method = POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WidgetConfigResource> createWidget(@PathVariable Integer dashboardId,
                                                             @RequestBody WidgetConfigResource widgetConfigResource) {
        WidgetConfigResourceAssembler assembler = new WidgetConfigResourceAssembler(dashboardId);
        WidgetConfig createdWidgetConfig =
                widgetService.saveWidget(assembler.toEntity(widgetConfigResource));
        WidgetConfigResource resource = assembler.toResource(createdWidgetConfig);
        return new ResponseEntity<>(resource, OK);
    }

    @RequestMapping(value = "/{widgetId}/data", method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SourceDataResource> getData(@PathVariable Integer dashboardId,
                                                      @PathVariable String widgetId) {
        SourceDataResourceAssembler assembler = new SourceDataResourceAssembler(dashboardId, widgetId);
        List<SourceData>            data      = widgetService.getData(widgetId);
        SourceDataResource          resource  = assembler.toResource(data);
        return new ResponseEntity<>(resource, OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedResources<WidgetConfigResource>> getWidgets(@PathVariable Integer dashboardId,
                                                                           @PageableDefault Pageable pageable,
                                                                           PagedResourcesAssembler pagedResourcesAssembler) {
        Page<WidgetConfig>            widgetConfigPage = widgetConfigRepository.findAll(pageable);
        WidgetConfigResourceAssembler assembler        = new WidgetConfigResourceAssembler(dashboardId);
        PagedResources<WidgetConfigResource> pagedResources =
                pagedResourcesAssembler.toResource(widgetConfigPage, assembler);
        return new ResponseEntity<>(pagedResources, OK);
    }

}
