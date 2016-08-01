package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;

public class WidgetConfigResource extends ResourceSupport {

    private String title;

    private Date lastModified;

    private List<SourceConfig> sourceConfigs = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<SourceConfig> getSourceConfigs() {
        return sourceConfigs;
    }

    public void setSourceConfigs(List<SourceConfig> sourceConfigs) {
        this.sourceConfigs = sourceConfigs;
    }
}
