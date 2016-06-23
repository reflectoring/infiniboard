package com.github.reflectoring.infiniboard.quartermaster.widget;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.reflectoring.infiniboard.packrat.source.SourceData;
import com.github.reflectoring.infiniboard.packrat.source.SourceDataRepository;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfig;
import com.github.reflectoring.infiniboard.packrat.widget.WidgetConfigRepository;

@Service
public class WidgetConfigService {

    private WidgetConfigRepository widgetConfigRepository;

    private SourceDataRepository sourceDataRepository;

    @Autowired
    public WidgetConfigService(WidgetConfigRepository widgetConfigRepository,
                               SourceDataRepository sourceDataRepository) {
        this.widgetConfigRepository = widgetConfigRepository;
        this.sourceDataRepository = sourceDataRepository;
    }

    public WidgetConfig loadWidget(String widgetId) {
        return widgetConfigRepository.findOne(widgetId);
    }

    public WidgetConfig saveWidget(WidgetConfig widgetConfig) {
        widgetConfig.setLastModified(LocalDate.now());
        return widgetConfigRepository.save(widgetConfig);
    }

    public List<SourceData> getData(String widgetId) {
        return sourceDataRepository.findAllByWidgetId(widgetId);
    }

    public List<WidgetConfig> loadWidgets() {
        return this.widgetConfigRepository.findAll();
    }
}
