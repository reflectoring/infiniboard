package com.github.reflectoring.infiniboard.packrat.widget;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * widget configuration, contains metadata and list of source configs
 */
public class WidgetConfig {

    @Id
    private String id;

    private String title;

    private LocalDate lastModified = LocalDate.now();

    private List<SourceConfig> sourceConfigs = new ArrayList<>();

    public WidgetConfig() {
    }

    public WidgetConfig(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public List<SourceConfig> getSourceConfigs() {
        return sourceConfigs;
    }

    public void setSourceConfigs(List<SourceConfig> sourceConfigs) {
        this.sourceConfigs = sourceConfigs;
    }

    public int size() {
        return sourceConfigs.size();
    }

    public boolean isEmpty() {
        return sourceConfigs.isEmpty();
    }

    public boolean contains(Object o) {
        return sourceConfigs.contains(o);
    }

    public boolean add(SourceConfig sourceConfig) {
        return sourceConfigs.add(sourceConfig);
    }

    public boolean remove(Object o) {
        return sourceConfigs.remove(o);
    }

    public void clear() {
        sourceConfigs.clear();
    }
}
