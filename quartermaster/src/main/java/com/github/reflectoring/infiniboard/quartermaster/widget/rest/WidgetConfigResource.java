package com.github.reflectoring.infiniboard.quartermaster.widget.rest;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.github.reflectoring.infiniboard.packrat.source.SourceConfig;

public class WidgetConfigResource extends ResourceSupport {

    @NotNull
    private String title;

    private String type;

    private String url;

    private String description;

    private Date lastModified;

    private List<SourceConfig> sourceConfigs = new ArrayList<>();

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
