package com.github.reflectoring.infiniboard.packrat.widget;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;

public class WidgetConfig {

    @Id
    private String id;

    private String title;

    private String type;

    private String url;

    private String description;

    private LocalDateTime lastModified = LocalDateTime.now();

    private List<SourceConfig> sourceConfigs = new ArrayList<>();

    public WidgetConfig() {
    }

    public WidgetConfig(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
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
